package com.jobeth.blog.service;

import com.jobeth.blog.dto.RoleDTO;
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

    /**
     * listAll
     *
     * @param roleDTO roleDTO
     * @return List<Role>
     */
    List<Role> listAll(RoleDTO roleDTO);

    /**
     * 更新角色菜单
     *
     * @param roleDTO roleDTO
     */
    void updateRole(RoleDTO roleDTO);
}
