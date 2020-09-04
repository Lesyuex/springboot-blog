package com.jobeth.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;

import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.StringUtils;
import com.jobeth.blog.dto.UserDTO;
import com.jobeth.blog.po.User;
import com.jobeth.blog.service.RoleService;
import com.jobeth.blog.service.UserService;
import com.jobeth.blog.vo.JsonResultVO;
import com.jobeth.blog.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户controller", tags = {"用户操作接口"})
public class UserController extends BaseController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    /**
     * 根据ID 获取
     *
     * @param id id
     * @return 获取结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Object> get(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            String exceptionMsg = "【 用户ID => " + id + " 不存在 】";
            return new JsonResultVO<>(ResultEnum.REQUEST_PARAMETER_ERROR, exceptionMsg);
        }
        return new JsonResultVO<>(user);
    }

    /**
     * 新增
     *
     * @param user user
     * @return 结果
     */
    @ApiOperation(value = "保存用户", tags = {"用户操作接口"}, httpMethod = "POST", response = JsonResultVO.class)
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody User user) {
        return userService.save(user) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 更新
     *
     * @param userDTO userDTO
     * @return 结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody UserDTO userDTO) {
        try {
            log.info("{}", JacksonUtil.objectToJson(userDTO));
            if (userDTO.getId() == 1) {
                return new JsonResultVO<>(ResultEnum.FAIL.getCode(), "不允许修改管理员信息");
            }
            userService.updateUserAndUserRole(userDTO);
            return new JsonResultVO<>();
        } catch (Exception e) {
            log.error("【 更新用户信息发生错误 】", e);
            return new JsonResultVO<>(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据ID 删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Long id) {
        return userService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    @GetMapping("/listByPage")
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", tags = {"用户操作接口"}, notes = "获取用户列表", response = JsonResultVO.class)
    public JsonResultVO<Object> listByPage(Page<User> page, User user) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        if (StringUtils.notNullAndEmpty(user.getUsername())) {
            queryWrapper.like(User::getUsername, user.getUsername());
        }
        if (StringUtils.notNullAndEmpty(user.getEnabled())) {
            queryWrapper.eq(User::getEnabled, user.getEnabled());
        }
        IPage<User> pageInfo = userService.page(page, queryWrapper);
        Page<UserVO> userVoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, userVoPage);
        List<UserVO> userVOList = new ArrayList<>();
        List<User> userList = pageInfo.getRecords();
        for (User item : userList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(item, userVO);
            userVOList.add(userVO);
        }
        userVoPage.setRecords(userVOList);
        return new JsonResultVO<>(userVoPage);
    }
}
