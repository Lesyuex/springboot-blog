package com.jobeth.blog.service.impl;

import com.jobeth.blog.config.CommonConfigProperties;
import com.jobeth.blog.po.Blog;
import com.jobeth.blog.mapper.BlogMapper;
import com.jobeth.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
@Slf4j
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    private final RedisService redisService;
    private final CommonConfigProperties properties;

    public BlogServiceImpl(RedisService redisService, CommonConfigProperties properties) {
        this.redisService = redisService;
        this.properties = properties;
    }

    @Override
    public Blog getById(Serializable id) {
        Blog blog = null;
        String blogRedisKey = properties.getRedisBlogPrefix() + id;
        //先从Redis查
        if (redisService.exists(blogRedisKey)) {
            blog = (Blog) redisService.get(blogRedisKey);
            log.info("【 文章ID => {} 数据来自于 Redis 】", id);
        }
        //从数据库取
        if (blog == null) {
            blog = super.getById(id);
            //缓存到Redis
            if (blog != null) {
                String key = properties.getRedisBlogPrefix() + id;
                redisService.setExpire(key, blog, properties.getBlogCacheTime());
            }
        }
        return blog;
    }

}
