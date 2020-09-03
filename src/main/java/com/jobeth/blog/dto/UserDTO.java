package com.jobeth.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 16:11
 */
@Data
public class UserDTO implements Serializable {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 盐
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态:1启用 0禁用
     */
    private Boolean enabled;


    private Integer status;

    private Long[] roleList;

}
