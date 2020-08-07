package com.jobeth.blog.service;

import com.jobeth.blog.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
public interface RoleService extends IService<Role> {

    List<Role> listByUserId(Long userId);
}
