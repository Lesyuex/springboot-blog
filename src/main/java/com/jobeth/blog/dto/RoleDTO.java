package com.jobeth.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * desc
 *
 * @author JyrpoKoo
 * @version [版本号 2020/8/28]
 * @date Created by IntelliJ IDEA in 2020/8/28 15:26
 */
@Data
public class RoleDTO {
    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    private Long userId;
}
