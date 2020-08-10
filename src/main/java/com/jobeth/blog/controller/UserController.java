package com.jobeth.blog.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;

import com.jobeth.blog.common.utils.JsonUtil;

import com.jobeth.blog.po.User;
import com.jobeth.blog.service.UserService;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            return new JsonResultVO<>(ResultEnum.REQUEST_PARAMETER_ERROR,exceptionMsg);
        }
        return new JsonResultVO<>(user);
    }

    /**
     * 新增
     *
     * @param user user
     * @return 结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody User user) {
        return userService.save(user) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 更新
     *
     * @param user user
     * @return 结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody User user) {
        LambdaUpdateWrapper<User> update = new UpdateWrapper<User>().lambda()
                .eq(User::getId, user.getId());
        return userService.update(user, update) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 根据ID 删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Integer id) {
        return userService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }
}
