package com.jobeth.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.TreeUtil;
import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.vo.MenuVO;
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
        //查找的是菜单，返回的是树状菜单数据
        if (permission.getType() == 1) {
            List<MenuVO> menuVOList = new ArrayList<>();
            if (!pageInfo.getRecords().isEmpty()) {
                for (Permission item : pageInfo.getRecords()) {
                    MenuVO menuVO = new MenuVO();
                    BeanUtils.copyProperties(item, menuVO);
                    menuVOList.add(menuVO);
                }
            }
            List<MenuVO> treeVOList = TreeUtil.generateTree(menuVOList, properties.getRootMenuId());
            resultVO.setData(treeVOList);
        }
        //查找的是权限
        else if (permission.getType() == 0) {
            resultVO.setData(pageInfo);
        }
        return resultVO;
    }
}
