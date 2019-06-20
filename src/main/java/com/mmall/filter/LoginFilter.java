package com.mmall.filter;

import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if(sysUser == null) {
            //如果用户没有登录,则跳转回登录页面
            String path = "/signin.jsp";
            response.sendRedirect(path);
            return;
        }
        //在登录的过滤器中,登录用户之后,在ThreadLocal中存入登录的用户信息,请求对象request,
        //在Http拦截器中,postHandle,afterCompletion中移除存入的信息
        RequestHolder.add(sysUser);
        RequestHolder.add(request);
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
