package com.jobeth.blog.mapper;

import com.jobeth.blog.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找
     * @param username username
     * @return User
     */
    User findByUsername(String username);
}
