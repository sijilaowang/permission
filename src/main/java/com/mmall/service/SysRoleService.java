package com.mmall.service;

import com.mmall.common.RequestHolder;
import com.mmall.dao.SequenceGeneratorMapper;
import com.mmall.dao.SysRoleMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SequenceGeneratorMapper sequenceGeneratorMapper;

    private static final String SEQ_SYS_ROLE = "SEQ_SYS_ROLE";

    public void updateById(RoleParam param) {
        if(null == param.getId()) {
            throw new ParamExcepiton("参数ID错误");
        }
        String operator = RequestHolder.getCurrentUser().getUsername();
        String ip = IpUtil.getRemoteIp(RequestHolder.getCurrentRequest());
        SysRole sysRole = SysRole.builder().id(param.getId()).name(param.getName())
                .status(param.getStatus()).remark(param.getRemark()).operator(operator)
                .operatorIp(ip).build();
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    /**
     * 根据id查找 Role
     * @param param
     * @return
     */
    public SysRole findById(RoleParam param) {
        if(null == param.getId()) {
            throw new ParamExcepiton("参数ID错误");
        }
        SysRole sysRole = sysRoleMapper.findById(param.getId());
        return sysRole;
    }

    public void deleteRole(RoleParam param) {
        if(null == param.getId()) {
            throw new ParamExcepiton("参数ID错误");
        }
        sysRoleMapper.deleteRoleById(param.getId());
    }

    public List<SysRole> findAllSysRole() {
        List<SysRole> list = sysRoleMapper.findAllSysRoleList();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    public void saveRole(RoleParam param) {
        BeanValidator.check(param);
        if(!checkExit(param.getName())) {
            throw new ParamExcepiton("角色名字已存在");
        }
        String operator = RequestHolder.getCurrentUser().getUsername();
        String ip = IpUtil.getRemoteIp(RequestHolder.getCurrentRequest());
        SysRole sysRole = SysRole.builder().id(sequenceGeneratorMapper.nextLongValue(SEQ_SYS_ROLE)).name(param
        .getName()).remark(param.getRemark()).status(param.getStatus()).build();
        sysRole.setOperator(operator);
        sysRole.setOperatorIp(ip);
        sysRole.setOperatorTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
    }

    /**
     * 检查角色名字是否存在 不存在返回true 存在返回false
     * @param roleName
     * @return
     */
    public boolean checkExit(String roleName) {
        int count = sysRoleMapper.countRoleByName(roleName);
        if(count <= 0) {
            return true;
        }
        return false;
    }
}
