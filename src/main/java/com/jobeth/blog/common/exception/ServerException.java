package com.jobeth.blog.common.exception;

import com.jobeth.blog.common.enums.ResultEnum;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 14:27
 */
public class ServerException extends RuntimeException {

    private ResultEnum resultEnum;

    public ServerException(ResultEnum resultEnum,String message) {
        super(message);
        this.resultEnum = resultEnum;
    }

    public ServerException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public ServerException(String message) {
        super(message);
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }
}
