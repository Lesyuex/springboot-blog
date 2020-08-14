package com.jobeth.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jobeth.blog.common.utils.TreeUtil;
import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.vo.MenuVO;
import com.jobeth.blog.vo.JsonResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    private final BlogProperties properties;
    private final PermissionService permissionService;

    public PermissionController(BlogProperties properties, PermissionService permissionService) {
        this.properties = properties;
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    public JsonResultVO<List<MenuVO>> list() {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.lambda().eq(Permission::getType, 1);
        List<Permission> list = permissionService.list(permissionQueryWrapper);
        List<MenuVO> menuVOList = new ArrayList<>();
        for (Permission permission : list) {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(permission, menuVO);
            menuVOList.add(menuVO);
        }
        List<MenuVO> treeVOList = TreeUtil.generateTree(menuVOList, properties.getRootMenuId());
        return new JsonResultVO<>(treeVOList);
    }
}
