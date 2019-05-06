package com.mmall.controller;

import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.PermissionException;
import com.mmall.model.SysAclModule;
import com.mmall.param.TestVO;
import com.mmall.util.BeanValidator;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    //@RequestMapping(value="/hello",produces="text/html;charset=UTF-8;")
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
        //throw new RuntimeException("11");
        return JsonData.success("hello");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVO vo){
       /* log.info("validate");
        //throw new RuntimeException("11");
        Map<String,String> map = BeanValidator.validate(vo);
        if(map != null || map.entrySet().size() > 0) {
            for (Map.Entry<String,String> entry: map.entrySet()
                 ) {
                System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
            }
        }
        return JsonData.success("hello");*/
       log.info("validator");
       SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
       SysAclModule sysAclModule = moduleMapper.selectByPrimaryKey(1L);
       log.info(JsonMapper.obj2String(sysAclModule));
       BeanValidator.check(vo);
       return JsonData.success("test validator");


    }
}
