package com.mmall.controller;


import com.mmall.beans.PageHelper;
import com.mmall.beans.PageVO;
import com.mmall.common.JsonData;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(AclParam aclParam) {
        return sysAclService.save(aclParam);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public  JsonData update(AclParam aclParam) {
        return sysAclService.update(aclParam);
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData findAclById(Long aclModuleId, PageHelper pageHelper) {
        int total = sysAclService.countAclById(aclModuleId);
        List<SysAcl> list = sysAclService.findAclById(aclModuleId,pageHelper);
        return JsonData.success(new PageVO<SysAcl>(list,total));
    }
}
