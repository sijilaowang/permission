package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/role.page")
    public ModelAndView rolePage() {
        return new ModelAndView("role");
    }

    /**
     * 返回所有角色信息
     * @return
     */
    @RequestMapping("/all.json")
    @ResponseBody
    public JsonData findAllSysRole() {
        List<SysRole> allSysRole = sysRoleService.findAllSysRole();
        return JsonData.success(allSysRole);
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.saveRole(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteRole(RoleParam param) {
        sysRoleService.deleteRole(param);
        return JsonData.success();
    }

    @RequestMapping("/find.json")
    @ResponseBody
    public JsonData findById(RoleParam param) {
        SysRole sysRole = sysRoleService.findById(param);
        return JsonData.success(sysRole);
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateById(RoleParam param) {
        sysRoleService.updateById(param);
        return JsonData.success();
    }

}
