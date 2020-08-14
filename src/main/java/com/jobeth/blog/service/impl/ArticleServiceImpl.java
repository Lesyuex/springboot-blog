package com.jobeth.blog.service.impl;

import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.po.Article;
import com.jobeth.blog.mapper.ArticleMapper;
import com.jobeth.blog.po.Blog;
import com.jobeth.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private final RedisService redisService;
    private final BlogProperties properties;

    public ArticleServiceImpl(RedisService redisService, BlogProperties properties) {
        this.redisService = redisService;
        this.properties = properties;
    }

    @Override
    public Article getById(Serializable id) {
        Article article = null;
        String blogRedisKey = properties.getRedisBlogPrefix() + id;
        //先从Redis查
        if (redisService.exists(blogRedisKey)) {
            article = (Article) redisService.get(blogRedisKey);
            log.info("【 文章ID => {} 数据来自于 Redis 】", id);
        }
        //从数据库取
        if (article == null) {
            article = super.getById(id);
            //缓存到Redis
            if (article != null) {
                String key = properties.getRedisBlogPrefix() + id;
                redisService.setExpire(key, article, properties.getBlogCacheTime());
            }
        }
        return article;
    }

}
