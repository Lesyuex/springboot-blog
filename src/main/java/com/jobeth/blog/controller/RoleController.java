package com.jobeth.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.StringUtils;
import com.jobeth.blog.dto.PermissionDTO;
import com.jobeth.blog.dto.RoleDTO;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.po.Role;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.service.RoleService;
import com.jobeth.blog.vo.JsonResultVO;
import com.jobeth.blog.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    public RoleController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Role> get(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        if (role == null) {
            String exceptionMsg = "【 角色ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(role);
    }

    /**
     * 保存
     *
     * @param role role
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Role role) {
        return roleService.save(role) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 更新
     *
     * @param roleDTO roleDTO
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody RoleDTO roleDTO) {
        try {
            //管理员信息不允许修改
            if (roleDTO.getId() == 1) {
                return new JsonResultVO<>(ResultEnum.FAIL.getCode(), "不允许修改管理员信息");
            }
            roleService.updateRole(roleDTO);
            return new JsonResultVO<>();
        } catch (Exception e) {
            log.error("【 更新角色信息发生错误 】", e);
            return new JsonResultVO<>(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Integer id) {
        return roleService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 列表查询分页查询
     *
     * @param roleDTO roleDTO
     * @return 列表数据
     */
    @GetMapping("/listByPage")
    public JsonResultVO<Object> listByPage(Page<Role> page, RoleDTO roleDTO) {
        LambdaQueryWrapper<Role> lambda = new QueryWrapper<Role>().lambda();
        IPage<Role> rolePage = roleService.page(page, lambda);
        IPage<RoleVO> roleVoPage = new Page<>();
        BeanUtils.copyProperties(rolePage, roleVoPage);
        List<RoleVO> roleVOList = new ArrayList<>();
        List<Role> roleList = rolePage.getRecords();
        for (Role role : roleList) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVO.setLabel(role.getName());
            roleVO.setValue(StringUtils.getValue(role.getId(), null));
            PermissionDTO permissionDTO = new PermissionDTO();
            permissionDTO.setRoleId(role.getId());
            permissionDTO.setType(1);
            //菜单
            List<Permission> menuList = permissionService.listPermission(permissionDTO);
            roleVO.setMenuIdList(menuList.stream().map(Permission::getId).toArray(Long[]::new));
            //权限
            permissionDTO.setType(0);
            List<Permission> permList = permissionService.listPermission(permissionDTO);
            roleVO.setPermIdList(permList.stream().map(Permission::getId).toArray(Long[]::new));
            roleVOList.add(roleVO);
        }
        roleVoPage.setRecords(roleVOList);
        return new JsonResultVO<>(roleVoPage);
    }

    @GetMapping("/listAll")
    public JsonResultVO<Object> list(RoleDTO roleDTO) {
        List<Role> roleList = roleService.listAll(roleDTO);
        List<RoleVO> roleVOList = new ArrayList<>();
        for (Role role : roleList) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVO.setLabel(role.getName());
            roleVO.setValue(StringUtils.getValue(role.getId(), null));
            roleVOList.add(roleVO);
        }
        return new JsonResultVO<>(roleVOList);
    }
}
