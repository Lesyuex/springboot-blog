package com.jobeth.blog.config;

import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.service.impl.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/8/3 11:10
 */
@Slf4j
@Component
public class PreDestroy implements ExitCodeGenerator, DisposableBean {
    private final BlogProperties properties;
    private final RedisService redisService;

    public PreDestroy(RedisService redisService, BlogProperties properties) {
        this.redisService = redisService;
        this.properties = properties;
    }

    @Override
    public void destroy() throws Exception {
        //清除相关keys
        log.info("【 调用了 PreDestroy 的 destroy() 方法 】");
        ///redisService.deleteByPrefix(properties.getRedisUrlPermKey() + "*");
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
