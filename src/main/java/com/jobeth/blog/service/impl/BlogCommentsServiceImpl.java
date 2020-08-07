package com.jobeth.blog.service.impl;

import com.jobeth.blog.po.BlogComments;
import com.jobeth.blog.mapper.BlogCommentsMapper;
import com.jobeth.blog.service.BlogCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客评论表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements BlogCommentsService {

}
