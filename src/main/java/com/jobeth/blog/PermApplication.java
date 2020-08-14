package com.jobeth.blog;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.jobeth.blog.common.properties.BlogProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * web应用程序启动类
 *
 * @author Jobeth
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.jobeth.blog.mapper")
public class PermApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermApplication.class, args);
    }

    /**
     * 项目的一些基本参数bean
     *
     * @return BlogProperties
     */
    @Bean("blogProperties")
    @ConfigurationProperties(prefix = "common")
    public BlogProperties blogProperties() {
        return new BlogProperties();
    }

    /**
     * 分页插件Bean
     *
     * @return 分页插件Bean
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


}
