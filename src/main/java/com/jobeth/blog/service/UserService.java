package com.jobeth.blog.service;

import com.jobeth.blog.dto.UserDTO;
import com.jobeth.blog.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户名查找
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 更新用户和用户角色
     * @param userDTO userDTO
     */
    void updateUserAndUserRole(UserDTO userDTO) ;
}
