package com.jobeth.blog.common;

import java.util.UUID;

/**
 * 常量
 *
 * @author Jobeth
 * @since 2020/6/30 14:37
 */
public class Constant {
    /**
     * 顶级菜单标识
     */
    public static final String ROOT_MENU = "0";
    /**
     * jwt的唯一身份标识
     */
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 博客缓存在Redis的前缀
     */
    public static final String BLOG_REDIS_KEY_PREFIX = "blogRedisKey";
    /**
     * 文章缓存时间
     */
    public final static Long BLOG_CACHE_TIME = 1000 * 60 * 60 * 24L;
}
