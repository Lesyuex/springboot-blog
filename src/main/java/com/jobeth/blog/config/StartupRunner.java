package com.jobeth.blog.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.service.impl.RedisService;
import io.swagger.models.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 通过order值的大小来决定启动的顺序
 *
 * @author Jobeth
 * @since 2020/8/2 19:19
 */
@Component
@Order(1)
@Slf4j
public class StartupRunner implements CommandLineRunner {

    private final PermissionService permissionService;
    private final RedisService redisService;
    private final BlogProperties properties;
    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper mapper;


    public StartupRunner(PermissionService permissionService,
                         RedisService redisService,
                         BlogProperties properties,
                         DocumentationCache documentationCache,
                         ServiceModelToSwagger2Mapper mapper) {
        this.permissionService = permissionService;
        this.redisService = redisService;
        this.properties = properties;
        this.documentationCache = documentationCache;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(String... args) {
        redisService.deleteByPrefix(properties.getRedisUrlPermKey() + "*");
        redisService.deleteByPrefix(properties.getRedisBlogPrefix() + "*");
        ///加载权限配置
        LambdaQueryWrapper<Permission> wrapper = new QueryWrapper<Permission>()
                .lambda().eq(Permission::getType, 0)
                .eq(Permission::getStatus, 1)
                .groupBy(Permission::getPath)
                .orderByDesc(Permission::getId);
        ///第一次查询
        List<Permission> list = permissionService.list(wrapper);
        ///把所有接口
        String groupName = Docket.DEFAULT_GROUP_NAME;
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        Swagger swagger = mapper.mapDocumentation(documentation);
        Map<String, Long> titleIdMap = Maps.newHashMap();
        list.forEach(permission -> titleIdMap.put(permission.getTitle(), permission.getId()));
        System.out.println(JacksonUtil.objectToJson(titleIdMap));
        ///保存每个接口 ApiController => 学生管理接口
        for (Tag tag : swagger.getTags()) {
            System.out.println(tag.getName());
            if (!titleIdMap.containsKey(tag.getName())) {
                Permission permission = new Permission();
                permission.setTitle(tag.getName());
                permission.setType(0);
                permission.setStatus(1);
                permission.setName(tag.getName());
                permission.setParentId(0L);
                permission.setPath(tag.getDescription());
                permissionService.save(permission);
            }
        }
        ///第二次查询
        list = permissionService.list(wrapper);
        ///保存已有的接口信息
        Map<String, Boolean> pathMethodMap = Maps.newHashMap();
        list.forEach(permission -> pathMethodMap.put(permission.getName() + permission.getMethod(), true));
        list.forEach(permission -> titleIdMap.put(permission.getTitle(), permission.getId()));
        // 加载到数据库
        for (Map.Entry<String, Path> item : swagger.getPaths().entrySet()) {
            String path = item.getKey();
            Path pathInfo = item.getValue();
            createApiIfNeeded(pathMethodMap, titleIdMap, path, pathInfo.getGet(), HttpMethod.GET.name());
            createApiIfNeeded(pathMethodMap, titleIdMap, path, pathInfo.getPost(), HttpMethod.POST.name());
            createApiIfNeeded(pathMethodMap, titleIdMap, path, pathInfo.getDelete(), HttpMethod.DELETE.name());
            createApiIfNeeded(pathMethodMap, titleIdMap, path, pathInfo.getPut(), HttpMethod.PUT.name());
        }

        //保证顺序鉴权
        if (!list.isEmpty()) {
            ///LinkedHashMap<String, String> urlPerm = (LinkedHashMap<String, String>) list.stream().collect(Collectors.toMap(Permission::getUrl, Permission::getPermission));
            LinkedHashMap<String, String> urlPermMap = new LinkedHashMap<>(100);
            list.forEach(permission -> {
                if (permission.getPath() != null && permission.getPerm() != null) {
                    urlPermMap.put(permission.getPath(), permission.getPerm());
                }
            });
            log.info("【 权限配置加载...... 】");
            redisService.setExpire(properties.getRedisUrlPermKey(), urlPermMap, 1000 * 60 * 60 * 12L);
        }
    }


    public void createApiIfNeeded(Map<String, Boolean> apiMap, Map<String, Long> titleIdMap, String path, Operation operation, String method) {
        if (operation == null) {
            return;
        }
        if (!apiMap.containsKey(path + method)) {
            apiMap.put(path + method, true);
            Permission permission = new Permission();
            permission.setMethod(method);
            permission.setName(path);
            String formatPath = path.replaceAll("[{][\\s\\S]*[}]", "*");
            permission.setPath(formatPath);
            permission.setTitle(formatPath);
            String perm1 = path.replaceFirst("/", "");
            String perm = perm1.replaceAll("/", ":");
            String formatPerm = perm.replaceAll("[{][\\s\\S]*[}]", "*");
            permission.setPerm(formatPerm);
            permission.setType(0);

            Long parentId = 0L;
            ///找到父id
            for (String tag : operation.getTags()) {
                if (titleIdMap.containsKey(tag)) {
                    parentId = titleIdMap.get(tag);
                    permission.setRemark(tag);
                }
            }
            permission.setParentId(parentId);
            // 保存
            permissionService.save(permission);
        }
    }

}
