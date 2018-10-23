package com.iscas.smurfs.common.auth.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.common.context.BaseContextHandler;
import com.iscas.smurfs.common.entity.dto.ResponseCode;
import com.iscas.smurfs.common.entity.dto.TokenForbiddenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ace on 2017/9/10.
 */
@Slf4j
public class UserAuthInterceptor extends HandlerInterceptorAdapter {

    @Value("${gate.ignore.startWith}")
    private String startWith;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        final String requestUri = request.getRequestURI();

        final String method = request.getMethod();
        //白名单
        if (isStartWith(requestUri)) {
            return null;
        }

        List<Permission> permissions = JSON.parseObject(adminFeign.getAllPermissions(),new TypeReference<List<Permission>>(){});
        List<Permission> result = permissions.parallelStream().filter(new Predicate<Permission>() {
            @Override
            public boolean test(Permission permission) {
                String url = permission.getUri();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                //String regEx = "^" + uri + "$";
                return (Pattern.compile(uri).matcher(requestUri).find() || requestUri.startsWith(url + "/"))
                        && method.equals(permission.getMethod());
            }
        }).collect(Collectors.toList());

        //该地址不受权限管理
        if (result==null || result.isEmpty()){
            return null;
        }
        UserJwtDto userJwtDto = null;
        try{
            userJwtDto = getUserJwtInfo(ctx, request);
            if (userJwtDto==null){
                //没有用户信息
                setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!")), ResponseCode.SUCCESS_CODE.getCode());
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Boolean isPermission = false;

        for(Permission permission : permissions){
            isPermission = adminFeign.checkUserAndPermission(userJwtDto.getUserId(),permission.getId());
            if (isPermission.equals(true)) break;
        }
        //没有授权
        if(!isPermission){
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!")),ResponseCode.SUCCESS_CODE.getCode());
        }else {
            ctx.addZuulRequestHeader("userId", userJwtDto.getUserId().toString());
            ctx.addZuulRequestHeader("userName", URLEncoder.encode(userJwtDto.getName()));
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
