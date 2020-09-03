package com.jobeth.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.JwtUtil;
import com.jobeth.blog.common.utils.TreeUtil;
import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.dto.PermissionDTO;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.vo.PermissionVO;
import com.jobeth.blog.vo.RouteMetaVO;
import com.jobeth.blog.vo.RouteVO;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class PermissionController extends BaseController {
    private final BlogProperties properties;
    private final PermissionService permissionService;

    public PermissionController(BlogProperties properties, PermissionService permissionService) {
        this.properties = properties;
        this.permissionService = permissionService;
    }


    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Permission> get(@PathVariable Integer id) {
        Permission permission = permissionService.getById(id);
        if (permission == null) {
            String exceptionMsg = "【 权限ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(permission);
    }

    /**
     * 保存
     *
     * @param permission permission
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Permission permission) {
        return permissionService.save(permission) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 更新
     *
     * @param permission permission
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody Permission permission) {
        return permissionService.updateById(permission) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Integer id) {
        return permissionService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 列表查询分页查询
     *
     * @param page       page
     * @param permission permission
     * @return 列表数据
     */
    @GetMapping("/listByPage")
    public JsonResultVO<Object> listByPage(Page<Permission> page, Permission permission) {
        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().lambda();
        if (permission.getType() != null) {
            queryWrapper.eq(Permission::getType, permission.getType());
        }
        IPage<Permission> pageInfo = permissionService.page(page, queryWrapper);
        JsonResultVO<Object> resultVO = new JsonResultVO<>();
        resultVO.setData(pageInfo);
        return resultVO;
    }

    /**
     * 获取用户菜单
     *
     * @return 用户菜单
     */
    @GetMapping("/listCurrentUserMenu")
    public JsonResultVO<Object> listCurrentUserRoutes() {
        String token = httpServletRequest.getHeader(properties.getJwtTokenName());
        String[] strings = token.split(properties.getJwtTokenPrefix());
        String userId = JwtUtil.getField(strings[1], "userId", String.class);
        if (userId == null) {
            throw new ServerException(ResultEnum.USER_TOKEN_INVALID);
        }
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setStatus(1);
        permissionDTO.setType(1);
        permissionDTO.setUserId(Long.parseLong(userId));
        List<Permission> permissions = permissionService.listPermission(permissionDTO);
        List<RouteVO> routeVOList = new ArrayList<>();
        for (Permission item : permissions) {
            RouteVO routeVO = new RouteVO();
            BeanUtils.copyProperties(item, routeVO);
            routeVO.setHidden(false);
            RouteMetaVO meta = new RouteMetaVO();
            meta.setTitle(item.getTitle());
            meta.setIcon(item.getIcon());
            meta.setAffix(false);
            routeVO.setMeta(meta);
            routeVOList.add(routeVO);
        }
        System.out.println(JacksonUtil.objectToJson(routeVOList));
        List<RouteVO> treeVOList = TreeUtil.generateTree(routeVOList, properties.getRootMenuId());
        System.out.println(JacksonUtil.objectToJson(treeVOList));
        return new JsonResultVO<>(treeVOList);
    }

    /**
     * 获取系统总菜单或者总权限
     *
     * @return 系统总菜单
     */
    @GetMapping("/listTreePermission")
    public JsonResultVO<Object> listTreePermission(Permission permission) {
        JsonResultVO<Object> resultVO = new JsonResultVO<>();
        LambdaQueryWrapper<Permission> eq = new QueryWrapper<Permission>().lambda().eq(Permission::getType, permission.getType()).eq(Permission::getStatus, 1);
        List<Permission> list = permissionService.list(eq);
        if (permission.getType() == 1) {
            List<RouteVO> routeList = new ArrayList<>();
            for (Permission item : list) {
                RouteVO routeVO = new RouteVO();
                BeanUtils.copyProperties(item, routeVO);
                routeList.add(routeVO);
            }
            List<RouteVO> treeMenu = TreeUtil.generateTree(routeList, properties.getRootMenuId());
            resultVO.setData(treeMenu);
        } else if (permission.getType() == 0) {
            List<PermissionVO> permissionVOList = new ArrayList<>();
            for (Permission item : list) {
                PermissionVO permissionVO = new PermissionVO();
                BeanUtils.copyProperties(item, permissionVO);
               permissionVOList.add(permissionVO);
            }
            List<PermissionVO> treeMenu = TreeUtil.generateTree(permissionVOList, properties.getRootMenuId());
            resultVO.setData(treeMenu);
        }
        return resultVO;
    }

    /**
     *
     * @param permissionDTO  ‘’
     * @return ‘’
     */
    @GetMapping("/listPermission")
    public JsonResultVO<Object> listPermission(PermissionDTO permissionDTO) {
        System.out.println(JacksonUtil.objectToJson(permissionDTO));
        List<Permission> permissions = permissionService.listPermission(permissionDTO);
        return new JsonResultVO<>(permissions);
    }

}
