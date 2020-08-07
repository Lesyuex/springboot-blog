package com.jobeth.blog.mapper;

import com.jobeth.blog.po.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
public interface BlogMapper extends BaseMapper<Blog> {
    
}
