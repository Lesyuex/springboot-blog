package com.jobeth.blog.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jobeth.blog.common.enums.ResultEnum;

import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JwtUtil;
import com.jobeth.blog.common.utils.ResponseUtil;

import com.jobeth.blog.common.properties.BlogProperties;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import com.jobeth.blog.service.impl.RedisService;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


/**
 * JwtFilter继承shiro AuthenticatingFilter重写认证方法
 *
 * @author Jobeth
 * @since 2020/6/30 16:41
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private final RedisService redisService;
    private final PermissionService permissionService;
    private final BlogProperties properties;
    PatternMatcher patternMatcher = new AntPathMatcher();

    public JwtFilter(RedisService redisService, PermissionService permissionService, BlogProperties properties) {
        this.redisService = redisService;
        this.permissionService = permissionService;
        this.properties = properties;
    }


    /**
     * 判断用户是否想要登录
     *
     * @param request  request
     * @param response response
     * @return boolean
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //从header中获取token
        String token = httpServletRequest.getHeader(properties.getJwtTokenName());
        return token != null;
    }

    /**
     * 验证用户
     *
     * @param request     request
     * @param response    response
     * @param mappedValue mappedValue
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!isLoginAttempt(httpServletRequest, httpServletResponse)) {
            ResponseUtil.writeJson(response, ResultEnum.USER_TOKEN_INVALID);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 创建用户的验证凭证
     *
     * @param servletRequest  servletRequest
     * @param servletResponse servletResponse
     * @return AuthenticationToken
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //从header中获取token
        String token = httpServletRequest.getHeader(properties.getJwtTokenName());
        return new CustomToken(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        //生成Token认证的信息
        CustomToken customToken = (CustomToken) createToken(request, response);
        Subject subject = getSubject(request, response);
        try {
            subject.login(customToken);
            log.info("【 userId:{} - 凭证正确，认证成功 】", customToken.getUserId());
            if (JwtUtil.needRefresh(customToken.getRealToken())) {
                //生成新token
                String userId = customToken.getUserId().toString();
                String newToken = properties.getJwtTokenPrefix() + JwtUtil.generateToken(userId);
                //存进redis
                String redisKey = properties.getJwtTokenPrefix() + userId;
                redisService.setExpire(redisKey, newToken, properties.getJwtExpiration());
                log.info("【 userId:{}- 凭证刷新，更新完成 】", userId);
            }
            return onLoginSuccess(customToken, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(customToken, e, request, response);
        }
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken authenticationToken, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        CustomToken customToken = (CustomToken) authenticationToken;
        String requestSource = httpServletRequest.getRequestURI();
        String userId = customToken.getUserId().toString();
        try {
            String systemSourcePerm = getSystemSourcePerm(customToken, requestSource);
            //鉴权
            subject.checkPermission(systemSourcePerm);
            log.info("【 userId:{} - 拥有 [ {} ] 访问权限,授权成功 】", userId, requestSource);
            return true;
        } catch (Exception e) {
            log.error("【 userId:{} - 无 [ {} ] 权限，授权失败 - {}】", userId, requestSource, e.getMessage());
            ResponseUtil.writeJson(response, ResultEnum.USER_ACCESS_DENIED);
            return false;
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        CustomToken customToken = (CustomToken) token;
        log.error("【 userId:{} 】- 凭证有误，认证失败- [{}]", customToken.getUserId(), e.getMessage());
        ResponseUtil.writeJson(response, ResultEnum.USER_TOKEN_INVALID);
        return false;
    }

    /**
     * 获取请求资源的对应权限
     *
     * @param requestSource requestSource
     * @return systemSourcePerm
     */
    @SuppressWarnings("unchecked")
    protected String getSystemSourcePerm(AuthenticationToken token, String requestSource) {
        String systemSource = null;
        String systemSourcePerm = null;
        CustomToken customToken = (CustomToken) token;
        try {
            //从redis取url对应的匹配权限
            if (redisService.exists(properties.getRedisUrlPermKey())) {
                LinkedHashMap<String, String> permMap = (LinkedHashMap<String, String>) redisService.get(properties.getRedisUrlPermKey());
                Set<String> permList = permMap.keySet();
                for (String pattern : permList) {
                    if (patternUrl(pattern, requestSource)) {
                        systemSource = pattern;
                        systemSourcePerm = permMap.get(pattern);
                        break;
                    }
                }
            }
            //没有在Redis拿到数据，从数据库取
            if (systemSourcePerm == null) {
                LambdaQueryWrapper<Permission> wrapper = new QueryWrapper<Permission>()
                        .lambda().eq(Permission::getType, 0)
                        .eq(Permission::getStatus, 0)
                        .groupBy(Permission::getPath)
                        .orderByDesc(Permission::getSortId);
                List<Permission> permissionList = permissionService.list(wrapper);
                for (Permission permission : permissionList) {
                    if (patternUrl(permission.getPath(), requestSource)) {
                        systemSource = permission.getPath();
                        systemSourcePerm = permission.getPerm();
                        break;
                    }
                }
            }
            if (systemSourcePerm == null) {
                throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE);
            }
        } catch (Exception e) {
            log.error("【 userId:{} 】-匹配资源权限发生内部异常 】", customToken.getUserId(), e);
        }
        log.info("【 系统资源 [{}] 匹配请求资源 [{}] 成功，所需权限 => [{}] 】", systemSource, requestSource, systemSourcePerm);
        return systemSourcePerm;
    }

    private boolean patternUrl(String systemSource, String requestSource) {
        return patternMatcher.matches(systemSource, requestSource);
    }
}
