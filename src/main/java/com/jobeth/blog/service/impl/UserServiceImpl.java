package com.jobeth.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.dto.UserDTO;
import com.jobeth.blog.mapper.RoleMapper;
import com.jobeth.blog.po.User;
import com.jobeth.blog.mapper.UserMapper;
import com.jobeth.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAndUserRole(UserDTO userDTO) {
        //管理员信息不允许修改
        if (userDTO.getId() == 1) {
            throw new ServerException("不允许修改管理员信息");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        LambdaUpdateWrapper<User> update = new UpdateWrapper<User>().lambda()
                .eq(User::getId, user.getId());
        //更新用户
        userMapper.update(user, update);
        Long[] roleList = userDTO.getRoleList();
        if (roleList != null) {
            //更新角色
            roleMapper.deleteRoleByUserId(user.getId());
            for (Long roleId : roleList) {
                roleMapper.insertUserRole(user.getId(), roleId);
            }
        }
    }
}
