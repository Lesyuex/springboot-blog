package com.jobeth.blog.common.utils;

import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/10/25]
 * @date Created by IntelliJ IDEA on 14:59 2019/10/25
 */
@Slf4j
public class ResponseUtil {
    private ResponseUtil() {
    }

    /**
     * 根据枚举类型输出结果
     *
     * @param response   response
     * @param resultEnum resultEnum
     */
    public static void writeJson(ServletResponse response, ResultEnum resultEnum) {
        JsonResultVO<Object> result = buildResult(resultEnum);
        writeJson(response, result);
    }

    /**
     * 响应Json数据
     *
     * @param response response
     * @param object   object
     */
    public static void writeJson(ServletResponse response, Object object) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JacksonUtil.objectToJson(object) + "");
            log.info("【 ResponseJson - {} 】", JacksonUtil.objectToJson(object));
        } catch (Exception e) {
            log.error("【 ResponseJson - 发生错误- {} 】", e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 操作正常结果
     *
     * @return 操作正常结果
     */
    public static <T> JsonResultVO<T> success() {
        return buildResult(ResultEnum.SUCCESS);
    }

    /**
     * 根据枚举类型输出结果
     *
     * @return 结果
     */
    public static <T> JsonResultVO<T> buildResult(ResultEnum resultEnum, T data) {
        JsonResultVO<T> jsonResultVO = new JsonResultVO<>();
        jsonResultVO.setCode(resultEnum.getCode());
        jsonResultVO.setMessage(resultEnum.getMessage());
        jsonResultVO.setData(data);
        return jsonResultVO;
    }

    /**
     * 操作失败结果
     *
     * @return 操作失败结果
     */
    public static <T> JsonResultVO<T> fail() {
        return fail(ResultEnum.FAIL);
    }

    /**
     * 根据枚举类型输出结果
     *
     * @return 结果
     */
    public static <T> JsonResultVO<T> buildResult(ResultEnum resultEnum) {
        return buildResult(resultEnum, null);
    }

    /**
     * 操作失败结果
     *
     * @return 操作失败结果
     */
    public static <T> JsonResultVO<T> fail(ResultEnum resultEnum) {
        return buildResult(resultEnum);
    }


}



