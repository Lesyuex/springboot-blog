package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * desc
 *
 * @author JyrpoKoo
 * @version [版本号 2020/8/28]
 * @date Created by IntelliJ IDEA in 2020/8/28 10:50
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    private Boolean enabled;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private Integer status;

    /**
     * 角色名
     */
    private String label;

    /**
     * 备注
     */
    private String value;

}
