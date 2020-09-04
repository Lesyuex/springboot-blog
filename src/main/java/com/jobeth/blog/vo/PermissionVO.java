package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc
 *
 * @author JyrpoKoo
 * @version [版本号 2020/8/14]
 * @date Created by IntelliJ IDEA in 2020/8/14 17:24
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionVO implements TreeVO<PermissionVO> {
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

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
    private String method;
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
    private List<PermissionVO> children;
    private Boolean hasChildren;
    /**
     * 状态 0启用 1禁用
     */
    private Integer status;
    private String remark;

    @Override
    public Object getId() {
        return this.id;
    }

    @Override
    public Object getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildren(List<PermissionVO> children) {
        this.children = children;
    }
}
