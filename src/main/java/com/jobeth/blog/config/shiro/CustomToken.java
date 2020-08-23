package com.jobeth.blog.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Desc
 *
 * @author Jobeth
 * @since 2020/6/30 15:20
 */
@Data
public class CustomToken implements AuthenticationToken {
    private Long userId;
    private String token;
    private String realToken;

    public CustomToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
