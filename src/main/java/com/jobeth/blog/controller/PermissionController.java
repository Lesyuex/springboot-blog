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
import com.jobeth.blog.vo.RouteMetaVO;
import com.jobeth.blog.vo.RouteVO;
import com.jobeth.blog.vo.JsonResultVO;
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
        return permissionService.save(permission) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 更新
     *
     * @param permission permission
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody Permission permission) {
        return permissionService.updateById(permission) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Integer id) {
        return permissionService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 列表查询分页查询
     *
     * @param page       page
     * @param permission permission
     * @return 列表数据
     */
    @GetMapping("/list")
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


    @GetMapping("/listCurrentUserRoutes")
    public JsonResultVO<Object> listCurrentUserRoutes() {
        String token = httpServletRequest.getHeader(properties.getJwtTokenName());
        String[] strings = token.split(properties.getJwtTokenPrefix());
        String userId = JwtUtil.getField(strings[1], "userId", String.class);
        if (userId == null) {
            throw new ServerException(ResultEnum.USER_TOKEN_INVALID);
        }
        PermissionDTO dto = PermissionDTO.builder().userId(Long.parseLong(userId)).status(1).type(1).build();
        List<Permission> permissions = permissionService.listByPermissionDTO(dto);
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
        List<RouteVO> treeVOList = TreeUtil.generateTree(routeVOList, properties.getRootMenuId());
        return new JsonResultVO<>(treeVOList);
    }
}
