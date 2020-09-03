package com.jobeth.blog.po;

import java.io.Serializable;

/**
 * desc
 *
 * @author JyrpoKoo
 * @version [版本号 2020/9/2]
 * @date Created by IntelliJ IDEA in 2020/9/2 16:25
 */
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tag;

    private String path;

    private String method;

    private String summary;

    private String operationId;

}
