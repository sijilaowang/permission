package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级存在相同的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        aclModule.setLevels(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperatorTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
    }

    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级存在相同的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"权限模块不存在");
        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevels(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date());
        sysAclModuleMapper.insertSelective(after);
    }

    @Transactional
    public void updateWithChild(AclModuleParam param) {

    }

    public boolean checkExist(Long parentId,String name,Long id) {
        return sysAclModuleMapper.countByNameAndParentId(parentId,name,id) > 0;
    }

    public String getLevel(Long id) {
        SysAclModule dept = sysAclModuleMapper.selectByPrimaryKey(id);
        if(dept == null) {
            return null;
        }
        return dept.getLevels();
    }

    public List<SysAclModule> findAll() {
        return sysAclModuleMapper.findAll();
    }
}
