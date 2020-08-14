package com.jobeth.blog.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 16:27
 */
@Data
public class BlogProperties {

    /**
     * JWT密钥KEY
     */
    public String jwtSecret;
    /**
     * JWTTokenKey
     */
    public String jwtTokenName;
    /**
     * JWTToken前缀字符
     */
    public String jwtTokenPrefix;
    /**
     * JWT过期时间
     */
    public Integer jwtExpiration;
    /**
     * JWT-token刷新的时间
     */
    public Integer jwtRefreshTime;
    /**
     * redis 权限配置信息key
     */
    public String redisUrlPermKey;

    /**
     * 不需要认证的接口
     */
    public String antMatcher;

    /**
     * 顶级菜单标识
     */
    public String rootMenuId;
    /**
     * jwt的唯一身份标识
     */
    public String jwtId = UUID.randomUUID().toString();

    /**
     * 博客缓存在Redis的前缀
     */
    public String redisBlogPrefix;
    /**
     * 文章缓存时间
     */
    public Long blogCacheTime;

}

