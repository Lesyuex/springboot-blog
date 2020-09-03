package com.jobeth.blog.service;

import com.jobeth.blog.dto.PermissionDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jobeth.blog.po.Permission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
public interface PermissionService extends IService<Permission> {

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
