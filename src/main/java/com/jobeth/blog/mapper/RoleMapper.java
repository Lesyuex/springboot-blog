package com.jobeth.blog.mapper;

import com.jobeth.blog.dto.RoleDTO;
import com.jobeth.blog.po.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 添加用户角色
     *
     * @param userId userId
     * @param roleId roleId
     */
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除用户角色
     *
     * @param userId userId
     */
    void deleteRoleByUserId(Long userId);

    /**
     * listAll
     *
     * @param roleDTO roleDTO
     * @return List<Role>
     */
    List<Role> listAll(RoleDTO roleDTO);

    /**
     * 删除角色菜单
     *
     * @param roleId roleId
     */
    void deleteRoleMenuAndPermByRoleId(@Param("roleId") Long roleId);

    /**
     * 插入角色菜单
     *
     * @param roleId roleId
     * @param menuId menuId
     */
    void insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}
