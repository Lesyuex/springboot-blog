package com.jobeth.blog.service.impl;

import com.jobeth.blog.po.ArticleComment;
import com.jobeth.blog.mapper.ArticleCommentMapper;
import com.jobeth.blog.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章评论表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

}
