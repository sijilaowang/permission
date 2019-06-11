package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.param.AclParam;
import com.mmall.util.BeanValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SequenceGeneratorService sequenceGeneratorService;

    public JsonData save(AclParam param) {
        BeanValidator.validate(param);
        if(checkExist(param.getName(),param.getAclModuleId())) {
            throw new ParamExcepiton("同一层级存在相同的权限点");
        }
        return null;
    }

    private boolean checkExist(String name,Integer aclModuleId) {
        return sysAclMapper.countSysAclByNameAndId(name,aclModuleId) == 0;
    }
}
