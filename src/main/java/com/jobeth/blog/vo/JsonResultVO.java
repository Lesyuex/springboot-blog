package com.jobeth.blog.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobeth.blog.common.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述
 *
 * @author Jobeth
 * @date Created by IntelliJ IDEA on 12:12 2020/4/10
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "返回数据Model")
public class JsonResultVO<T> {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "接口状态码", name = "code", required = true)
    private Integer code;
    /**
     * 信息
     */
    @ApiModelProperty(value = "接口信息提示", name = "message", required = true)
    private String message;
    @ApiModelProperty(value = "数据", name = "data")
    private T data;

    public JsonResultVO() {
        this.code = 200;
        this.message = "操作成功！";
    }

    public JsonResultVO(T data) {
        this.code = 200;
        this.message = "操作成功！";
        this.data = data;
    }

    public JsonResultVO(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public JsonResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonResultVO(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public JsonResultVO(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }
}
