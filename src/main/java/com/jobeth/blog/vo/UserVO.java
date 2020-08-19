package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 16:08
 */
@Data
public class UserVO {
    private Long id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态:1启用 0禁用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
