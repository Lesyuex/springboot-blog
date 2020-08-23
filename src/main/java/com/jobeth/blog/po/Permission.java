package com.jobeth.blog.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 权限名
     */
    private String name;

    /**
     * 资源类型:0:请求资源，1：菜单
     */
    private Integer type;
    /**
     * 重定向路径
     */
    private String redirect;
    /**
     * 资源路径或路由路径
     */
    private String path;

    /**
     * 资源对应权限
     */
    private String perm;

    /**
     * 组件资源(用于匹配component组件)
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 备注
     */
    private String title;

    /**
     * 排序
     */
    private Integer sortId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态 0启用 1禁用
     */
    private Integer status;

}
