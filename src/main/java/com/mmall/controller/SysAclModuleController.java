package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import com.mmall.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        return sysAclModuleService.save(param);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        return sysAclModuleService.update(param);
    }

    @RequestMapping("/findAll.json")
    @ResponseBody
    public JsonData findAll() {
        List<SysAclModule> list = sysAclModuleService.findAll();
        return JsonData.success(list);
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData findAll2Tree() {
        List<AclModuleLevelDto> aclModuleLevelDtos = sysTreeService.aclModuleTree();
        return JsonData.success(aclModuleLevelDtos);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteAclModule(Long id) {
        return sysAclModuleService.deleteAclModule(id);
    }

    @RequestMapping("/findById.json")
    @ResponseBody
    public JsonData findById(Long id) {
        return sysAclModuleService.findById(id);
    }
}
