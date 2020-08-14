package com.jobeth.blog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Http工具类
 *
 * @author Jobeth
 */
public class WebHttpUtil {

    /**
     * 获取分页参数
     *
     * @param request request
     * @param <T>     T
     * @return IPage<T>
     */
    public static <T> IPage<T> getPage(HttpServletRequest request) {
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        long current = pageIndex != null ? Long.parseLong(pageIndex) : 0L;
        long size = pageSize != null ? Long.parseLong(pageSize) : 10L;
        return new Page<>(current, size);
    }
}
