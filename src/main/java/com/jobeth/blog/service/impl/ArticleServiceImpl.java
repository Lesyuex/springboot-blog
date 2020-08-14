package com.jobeth.blog.service.impl;

import com.jobeth.blog.po.Article;
import com.jobeth.blog.mapper.ArticleMapper;
import com.jobeth.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
