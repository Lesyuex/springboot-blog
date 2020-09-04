package com.jobeth.blog.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.jobeth.blog.common.helper.MybatisBatchHelper;
import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.StringUtils;
import com.jobeth.blog.mapper.PermissionMapper;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.service.impl.RedisService;
import io.swagger.models.*;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.SqlSession;
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
public class StartupRunner1 implements CommandLineRunner {

    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper mapper;


    public StartupRunner1(DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper) {
        this.documentationCache = documentationCache;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(String... args) {
        ///加载权限配置
        LambdaQueryWrapper<Permission> wrapper = new QueryWrapper<Permission>()
                .lambda().eq(Permission::getType, 0)
                .eq(Permission::getStatus, 1)
                .orderByDesc(Permission::getId);
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisBatchHelper.openSession();
            PermissionMapper permissionMapper = MybatisBatchHelper.getMapper(sqlSession, PermissionMapper.class);
            ///第一次查询
            List<Permission> list = permissionMapper.selectList(wrapper);
            ///把所有接口
            String groupName = Docket.DEFAULT_GROUP_NAME;
            Documentation documentation = documentationCache.documentationByGroup(groupName);
            Swagger swagger = mapper.mapDocumentation(documentation);
            Map<String, Long> titleIdMap = Maps.newHashMap();
            list.forEach(permission -> titleIdMap.put(permission.getTitle(), permission.getId()));
            ///保存每个接口 ApiController => 学生管理接口
            for (Tag tag : swagger.getTags()) {
                if (!titleIdMap.containsKey(tag.getName())) {
                    Permission permission = new Permission();
                    permission.setTitle(tag.getName());
                    permission.setType(0);
                    permission.setStatus(1);
                    permission.setParentId(0L);
                    permission.setName("-");
                    permission.setRemark(tag.getDescription());
                    permissionMapper.insert(permission);
                }
            }
            ///第二次查询
            list = permissionMapper.selectList(wrapper);
            ///保存已有的接口信息
            Map<String, Boolean> pathMethodMap = Maps.newHashMap();
            list.forEach(permission -> pathMethodMap.put(permission.getName() + permission.getMethod(), true));
            list.forEach(permission -> titleIdMap.put(permission.getTitle(), permission.getId()));
            // 加载到数据库
            for (Map.Entry<String, Path> item : swagger.getPaths().entrySet()) {
                String path = item.getKey();
                Path pathInfo = item.getValue();
                createApiIfNeeded(permissionMapper, pathMethodMap, titleIdMap, path, pathInfo.getGet(), HttpMethod.GET.name());
                createApiIfNeeded(permissionMapper, pathMethodMap, titleIdMap, path, pathInfo.getPost(), HttpMethod.POST.name());
                createApiIfNeeded(permissionMapper, pathMethodMap, titleIdMap, path, pathInfo.getDelete(), HttpMethod.DELETE.name());
                createApiIfNeeded(permissionMapper, pathMethodMap, titleIdMap, path, pathInfo.getPut(), HttpMethod.PUT.name());
            }
            MybatisBatchHelper.commit(sqlSession);
            MybatisBatchHelper.close(sqlSession);
            log.info("【 加载接口信息完成 】");
        } catch (Exception e) {
            log.error("【 加载接口信息发生异常 】", e);
            MybatisBatchHelper.rollback(sqlSession);
        }
    }


    public void createApiIfNeeded(PermissionMapper permissionMapper, Map<String, Boolean> apiMap, Map<String, Long> titleIdMap, String path, Operation operation, String method) {
        if (operation == null) {
            return;
        }
        if (!apiMap.containsKey(path + method)) {
            apiMap.put(path + method, true);
            Permission permission = new Permission();
            String formatPath = path.replaceAll("[{][\\s\\S]*[}]", "*");
            String str = StringUtils.getValue(operation.getSummary(), "") + ": " + formatPath;
            permission.setName(path);
            permission.setTitle(str);
            Long parentId = 0L;
            ///找到父id
            for (String tag : operation.getTags()) {
                if (titleIdMap.containsKey(tag)) {
                    parentId = titleIdMap.get(tag);
                    permission.setRemark(tag);
                }
            }
            permission.setParentId(parentId);
            permission.setPath(formatPath);
            permission.setMethod(method);
            ///
            String perm1 = path.replaceFirst("/", "");

            String perm = perm1.replaceAll("/", ":");
            String formatPerm = perm.replaceAll("[{][\\s\\S]*[}]", "*");
            permission.setPerm(formatPerm);
            permission.setType(0);
            // 保存
            permissionMapper.insert(permission);
        }
    }

}
