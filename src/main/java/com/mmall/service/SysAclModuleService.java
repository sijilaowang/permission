package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.common.Sequence;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SequenceGeneratorService sequenceGeneratorService;

    public JsonData save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级存在相同的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        aclModule.setId(sequenceGeneratorService.nextLongValue(Sequence.SYS_ACLMODULE_ID_SEQ));
        aclModule.setLevels(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperatorTime(new Date());
        int count = sysAclModuleMapper.insertSelective(aclModule);
        if(count == 1) {
            return JsonData.success();
        }
        return JsonData.fail("保存失败");
    }

    public JsonData update(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级存在相同的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"权限模块不存在");
        SysAclModule after = SysAclModule.builder().id(before.getId()).name(param.getName()).parentId(param.getParentId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevels(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date());
        int count = sysAclModuleMapper.insertSelective(after);
        if(count == 1) {
            return JsonData.success();
        }
        return JsonData.fail("更新失败");
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

    public JsonData deleteAclModule(Long id) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(sysAclModule.getLevels())) {
            //判断当前层级下面是否存在子层级,判断规则
            //id  name    parentId   level
            //22 公共管理   0         0
            //23 设备管理   22        0.22
            //24 服务器管理 23         0.22.23
            //判断 level.id % 下是否存在 数据
            int count = sysAclModuleMapper.checkHasChildren(sysAclModule.getLevels() + "." + sysAclModule.getId());
            if(count > 0) {
                return JsonData.fail("删除权限模块出错,存在下级权限模块");
            }
        }
        int result = sysAclModuleMapper.deleteByPrimaryKey(id);
        if(result > 0) {
            return JsonData.success();
        } else {
            return JsonData.fail("删除权限模块出错");
        }
    }

    public JsonData findById(Long id) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(id);
        if(aclModule != null) {
            return JsonData.success(aclModule);
        } else {
            return JsonData.fail("未查到权限模块记录");
        }
    }
}
