package com.mmall.service;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 核心权限获取
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public List<SysAcl> getCurrentUserAclList() {
        Long userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    public List<SysAcl> getRoleAclList(Long roleId) {
        List<Long> list = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Long>newArrayList(roleId));
        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(list);
    }

    public List<SysAcl> getUserAclList(Long userId) {
        if(isSuperAdmin()) {
            //获取所有权限
            return sysAclMapper.findAll();
        }
        List<Long> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        //如果用户没有分配任何角色,则返回一个空集
        if(CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        //如果有,则需要返回权限的并集
        List<Long> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        //如果这些角色都没有权限,也返回空集
        if(CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        //根据id查询出所有权限
        return sysAclMapper.getByIdList(userAclIdList);
    }

    //判断是否是超级管理员权限,如果是的话,超级管理员能把所有的权限分配出去
    public boolean isSuperAdmin() {
       return true;
    }
}
