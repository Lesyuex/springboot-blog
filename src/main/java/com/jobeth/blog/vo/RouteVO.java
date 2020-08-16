package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RouteVO implements TreeVO<RouteVO> {
    /**
     * 权限id
     */
    @JsonIgnore
    private Integer id;

    /**
     * 路由名字
     */
    private String name;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 父级id
     */
    @JsonIgnore
    private Integer parentId;

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
    public Integer getId() {
        return id;
    }


    @Override
    public Integer getParentId() {
        return parentId;
    }


    @Override
    public void setChildren(List<RouteVO> childrenList) {
        this.children = childrenList;
    }

}