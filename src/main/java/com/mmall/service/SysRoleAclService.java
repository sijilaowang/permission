package com.mmall.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysRoleAcl;
import com.mmall.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mmall.common.Sequence.SYS_ROLE_ACL_ID_SEQ;


@Service
public class SysRoleAclService {

    private static final int ERROR_CODE = 0;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SequenceGeneratorService sequenceGeneratorService;


    @Transactional
    public void saveRoleAcl(Long roleId,String selectedIds) {
        if(roleId == null) {
            throw new ParamExcepiton("selectedIds参数异常");
        }
        if(StringUtils.isBlank(selectedIds)) {
            throw new ParamExcepiton("selectedIds参数异常");
        }
        List<Long> idList = null;
        try {
            List<String> ids = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(selectedIds);
            idList = ids.stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ParamExcepiton("selectedIds参数异常");
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        //先删除角色下所有

        if(1 == 1) {
            sysRoleAclMapper.deleteByRoleId(roleId);
            throw new RuntimeException();
        }
        for(Long id : idList) {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().id(sequenceGeneratorService.nextLongValue(SYS_ROLE_ACL_ID_SEQ)).aclId(id).
                    roleId(roleId).operator(RequestHolder.getCurrentUser().getUsername()).operatorTime(new Date()).operatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).build();
            roleAclList.add(sysRoleAcl);
        }

        sysRoleAclMapper.batchInsert(roleAclList);
    }
}
