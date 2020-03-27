package com.company.project.configurer;

import com.alibaba.fastjson.JSON;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {


    /**
     * 认证之前执行该方法
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        return  null != subject && subject.isAuthenticated();
    }

    /**
     * 认证未通过执行该方法
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response){
        //完成token登入
        //1.检查请求头中是否含有token
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        //2. 如果客户端没有携带token，拦下请求
        if(null==token||"".equals(token)){
            response401(response,"未携带Token，禁止访问接口");
            return false;
        }
        //3. 如果有，对进行进行token验证
        JWTToken jwtToken = new JWTToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            response401(response,e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            Result<Object> result = new Result<>();
            result.setCode(ResultCode.UNAUTHORIZED);
            result.setMessage(msg);
            out.append(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}