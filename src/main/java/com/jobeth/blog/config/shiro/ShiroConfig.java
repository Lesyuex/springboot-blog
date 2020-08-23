package com.jobeth.blog.config.shiro;

import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.service.impl.RedisService;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 *
 * @author Jobeth
 * @since 2020/6/30 15:08
 */
@Configuration
public class ShiroConfig {
    private final RedisService redisService;
    private final PermissionService permissionService;

    private final BlogProperties properties;

    public ShiroConfig(RedisService redisService, PermissionService permissionService, BlogProperties properties) {
        this.redisService = redisService;
        this.permissionService = permissionService;
        this.properties = properties;
    }

    /**
     * 创建自定义realm认证授权bean
     *
     * @return JwtRealm
     */
    @Bean
    public CustomRealm jwtRealm() {
        CustomRealm customRealm = new CustomRealm();
        //基于权限授权
        customRealm.setPermissionResolver(new JwtPermissionResolver());
        return customRealm;
    }


    @Bean("shiroManager")
    public DefaultSecurityManager defaultWebSecurityManager(@Qualifier("jwtRealm") CustomRealm customRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("shiroManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter(redisService, permissionService,properties));
        shiroFilter.setFilters(filterMap);

        // 配置不会被拦截的链接 顺序判断
        LinkedHashMap<String, String> authMap = new LinkedHashMap<>(8);
        //不需要拦截的接口
        String[] antMatcher = properties.getAntMatcher().split(",");
        if (antMatcher.length>0){
            for (String s : antMatcher) {
                authMap.put(s,"anon");
            }
        }
        authMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(authMap);
        return shiroFilter;
    }

}
