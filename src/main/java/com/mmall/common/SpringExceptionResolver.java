package com.mmall.common;

import com.mmall.exception.ParamExcepiton;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        //数据请求 .json结尾, 页面请求 .page 结尾
        if(url.endsWith(".json")) {
            if(e instanceof PermissionException || e instanceof ParamExcepiton) {
                JsonData result = JsonData.fail(e.getMessage());
                /*viewName jsonView 对应spring-servlet.xml 配置文件中的bean 把数据作为json格式返回*/
                mv = new ModelAndView("jsonView",result.toMap());
            } else {
                /*处理未知异常,打印url和异常堆栈信息*/
                log.error("unknow json exception,url:" + url,e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",result.toMap());
            }
        } else if(url.endsWith(".page")) {
            log.error("unknow page exception,url:" + url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",result.toMap());
        } else {
            log.error("unknow exception,url:" + url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}
