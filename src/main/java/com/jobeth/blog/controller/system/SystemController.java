package com.jobeth.blog.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.JwtUtil;

import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.controller.BaseController;
import com.jobeth.blog.dto.UserDTO;
import com.jobeth.blog.po.User;
import com.jobeth.blog.service.UserService;
import com.jobeth.blog.service.impl.RedisService;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 16:05
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class SystemController extends BaseController {

    private final UserService userService;
    private final RedisService redisService;
    private final BlogProperties properties;

    public SystemController(UserService userService, RedisService redisService, BlogProperties properties) {
        this.userService = userService;
        this.redisService = redisService;
        this.properties = properties;
    }

    @GetMapping("/login")
    public JsonResultVO<String> login( UserDTO userDTO) {
        log.info("【 用户登录-{} 】", JacksonUtil.objectToJson(userDTO));
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDTO.getUsername()));
        //用户不存在或密码不一致
        if (user == null || !user.getPassword().equals(userDTO.getPassword())) {
            throw new ServerException(ResultEnum.USER_LOGIN_FAIL);
        }
        log.info("【 用户校验成功，开始生成用户Token...... 】");
        //生成JwtToken
        String token = JwtUtil.generateToken(user.getId().toString());
        if (token == null) {
            throw new ServerException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        //存入Redis
        String redisKey = properties.getJwtTokenPrefix() + user.getId();
        String redisToken = properties.getJwtTokenPrefix() + token;
        try {
            redisService.setExpire(redisKey, redisToken, properties.getJwtExpiration());
        } catch (Exception e) {
            log.error("【 用户登录-服务器内部错误 】", e);
            throw new ServerException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        return new JsonResultVO<>(redisToken);
    }

    @PostMapping("/test/get/{id}")
    public JsonResultVO<Integer> test(@PathVariable Integer id) {
        return new JsonResultVO<>(ResultEnum.SUCCESS, id);
    }
}
