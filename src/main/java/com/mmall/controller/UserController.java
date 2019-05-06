package com.mmall.controller;

import com.mmall.model.SysUser;
import com.mmall.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SysUser user = sysUserService.findByKeyword(username);
        String errMsg = "";
        String ret = request.getParameter("ret");
        if(StringUtils.isBlank(username)) {
            errMsg = "用户名不能为空";
        } else if(StringUtils.isBlank(password)) {
            errMsg = "密码不能为空";
        } else if(user == null) {
            errMsg = "用户名不存在";
        }else if(!user.getPassword().equals(DigestUtils.md5Hex(password))) {
            errMsg = "用户名或者密码错误";
        } else if(user.getStatus() != 1) {
            errMsg = "用户已被冻结,请联系管理员";
        } else {
            //登录成功
            request.getSession().setAttribute("user",user);
            if(StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
            }
        }

        request.setAttribute("errMsg",errMsg);
        request.setAttribute("username",username);
        if(StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret",ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request,response);

    }

    @RequestMapping("/logout.page")
    public String logout() {
        return "signin";
    }
}
