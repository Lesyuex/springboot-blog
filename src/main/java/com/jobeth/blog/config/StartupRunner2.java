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
@Order(2)
@Slf4j
public class StartupRunner2 implements CommandLineRunner {

    private final PermissionService permissionService;
    private final RedisService redisService;
    private final BlogProperties properties;


    public StartupRunner2(PermissionService permissionService,
                          RedisService redisService,
                          BlogProperties properties,
                          DocumentationCache documentationCache,
                          ServiceModelToSwagger2Mapper mapper) {
        this.permissionService = permissionService;
        this.redisService = redisService;
        this.properties = properties;
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
        List<Permission> list = permissionService.list(wrapper);
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
}
