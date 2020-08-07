package com.jobeth.blog.service.impl;

import com.jobeth.blog.po.Category;
import com.jobeth.blog.mapper.CategoryMapper;
import com.jobeth.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
