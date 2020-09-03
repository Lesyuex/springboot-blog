package com.jobeth.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobeth.blog.dto.PermissionDTO;
import com.jobeth.blog.po.Permission;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获取用户权限或者用户菜单
     *
     * @param permissionDTO permissionDTO
     * @return List<Permission>
     */
    List<Permission> listPermission(PermissionDTO permissionDTO);

    /**
     * 获取用户权限或者用户菜单
     *
     * @param permissionDTO permissionDTO
     * @return List<Permission>
     */
    List<Permission> listRolePermission(PermissionDTO permissionDTO);
}
