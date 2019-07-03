package com.mmall.controller;

import com.mmall.beans.PageHelper;
import com.mmall.beans.PageVO;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/allList.json")
    @ResponseBody
    public JsonData allUserList(PageHelper pageHelper) {
        int count = sysUserService.countUser(null);
        List<SysUser> allSysUser = sysUserService.findAllSysUserPage(pageHelper);
        return JsonData.success(new PageVO<SysUser>(allSysUser,count));
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData userList(UserParam param,PageHelper pageHelper) {
        int total = sysUserService.countUser(param.getDeptId());
        List<SysUser> allSysUser = sysUserService.findSysUserByDeptId(param,pageHelper);
        return JsonData.success(new PageVO<SysUser>(allSysUser,total));
    }

    @RequestMapping("/findByUserId.json")
    @ResponseBody
    public JsonData findUserById(UserParam param) {
        SysUser sysUser = sysUserService.findUserById(param.getId());
        return JsonData.success(sysUser);
    }

    @RequestMapping("/findUserList.json")
    @ResponseBody
    public JsonData findAllUser(Long roleId) {
        return JsonData.success(sysUserService.findUserByRoleId(roleId));
    }
}
