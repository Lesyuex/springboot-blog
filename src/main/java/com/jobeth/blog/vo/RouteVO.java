package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 14:36
 */
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteVO implements TreeVO<RouteVO> {
    /**
     * 权限id
     */
    private Long id;

    /**
     * 路由名字
     */
    private String name;
    private String title;
    /**
     * 重定向路径
     */
    private String redirect;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 父级id
     */
    @JsonIgnore
    private Long parentId;

    private Boolean hidden;

    /**
     * 组件资源(用于匹配component组件)
     */
    private String component;

    private RouteMetaVO meta;

    /**
     * 子菜单
     */
    private List<RouteVO> children;

    @Override
    public Long getId() {
        return id;
    }


    @Override
    public Long getParentId() {
        return parentId;
    }


    @Override
    public void setChildren(List<RouteVO> childrenList) {
        this.children = childrenList;
    }

}