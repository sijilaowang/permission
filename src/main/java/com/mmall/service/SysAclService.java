package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageHelper;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.common.Sequence;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SequenceGeneratorService sequenceGeneratorService;

    public int countAclById(Long aclModuleId) {
        return sysAclMapper.countAclById(aclModuleId);
    }

    public List<SysAcl> findAclById(Long aclModuleId,PageHelper pageHelper) {
        if(aclModuleId == null) {
            return null;
        }
        List<SysAcl> list = sysAclMapper.selectByAclModuleId(aclModuleId,pageHelper);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    public JsonData save(AclParam param) {
        BeanValidator.validate(param);
        if(checkExist(param.getName(),param.getAclModuleId())) {
            throw new ParamExcepiton("同一层级存在相同的权限点");
        }
        SysAcl sysAcl = SysAcl.builder().id(sequenceGeneratorService.nextLongValue(Sequence.SYS_ACL_ID_SEQ))
                .aclModuleId(param.getAclModuleId()).name(param.getName()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperatorTime(new Date());
        int count = sysAclMapper.insert(sysAcl);
        if(count == 1) {
            return JsonData.success();
        }
        return JsonData.fail("保存失败");
    }

    public JsonData update(AclParam param) {
        BeanValidator.validate(param);
        if(checkExist(param.getName(),param.getAclModuleId())) {
            throw new ParamExcepiton("同一层级存在相同的权限点");
        }
        SysAcl beforeSysAcl = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(beforeSysAcl,"权限点不存在");
        SysAcl afterSysAcl = beforeSysAcl.builder().id(beforeSysAcl.getId())
                .aclModuleId(param.getAclModuleId()).remark(param.getRemark())
                .seq(param.getSeq()).status(param.getStatus()).type(param.getType())
                .url(param.getUrl()).name(param.getName()).build();
        int count = sysAclMapper.updateByPrimaryKeySelective(afterSysAcl);
        if(count == 1) {
            return JsonData.success();
        }
        return JsonData.fail("更新失败");
    }

    private boolean checkExist(String name,Long aclModuleId) {
        return sysAclMapper.countSysAclByNameAndId(name,aclModuleId) == 0;
    }
}
