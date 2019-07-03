package com.mmall.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.beans.PageHelper;
import com.mmall.beans.PageVO;
import com.mmall.dao.SequenceGeneratorMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysRoleUser;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.util.BeanValidator;
import javafx.util.Builder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SequenceGeneratorMapper sequenceGenerator;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    private static final String SYS_USER_ID_SEQ = "SYS_USER_ID_SEQ";

    public void save(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(),param.getId())) {
            throw new ParamExcepiton("电话号码已被占用");
        }
        if(checkTelephoneExist(param.getMail(),param.getId())) {
            throw new ParamExcepiton("邮箱已被占用");
        }
        String password = "123456";
        String encayptPassword = DigestUtils.md5Hex(password);
        SysUser sysUser = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encayptPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        sysUser.setId(sequenceGenerator.nextLongValue(SYS_USER_ID_SEQ));
        sysUser.setOperator("system");
        sysUser.setOperatorIp("127.0.0.1");
        sysUser.setOperatorTime(new Date());
        //TODO  sendEmail
        sysUserMapper.insertSelective(sysUser);

    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(),param.getId())) {
            throw new ParamExcepiton("电话号码已被占用");
        }
        if(checkTelephoneExist(param.getMail(),param.getId())) {
            throw new ParamExcepiton("邮箱已被占用");
        }

        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(before.getPassword()).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    public List<SysUser> findAllSysUser() {
        return sysUserMapper.findAllSysUser();
    }

    public List<SysUser> findSysUserByDeptId(UserParam param,PageHelper pageHelper) {
        return sysUserMapper.findSysUserPage(param.getDeptId(),pageHelper);
    }

    public SysUser findByKeyword(String str) {
        return sysUserMapper.findByKeyword(str);
    }

    public boolean checkEmailExist(String mail, Long id) {
        return sysUserMapper.countByMail(mail,id) > 0;
    }

    public boolean checkTelephoneExist(String telephone, Long id) {
        return sysUserMapper.countByTelephone(telephone,id) > 0;
    }

    public int countUser(Long deptId) {
        return sysUserMapper.countUser(deptId);
    }

    public List<SysUser> findAllSysUserPage(PageHelper pageHelper) {
        return sysUserMapper.findAllSysUserPage(pageHelper);
    }

    public SysUser findUserById(Long id) {
        return sysUserMapper.findUserById(id);
    }

    public Map<String,List<SysUser>> findUserByRoleId(Long roleId) {
        Map<String,List<SysUser>> map = Maps.newHashMap();
        List<Long> userIdList = sysRoleUserMapper.findUserIdByRoleId(roleId);
        List<SysUser> selectedUserList = sysUserMapper.findSelectedUserByRoleId(userIdList);
        List<SysUser> unSelectedUserList = sysUserMapper.findUnSelectedUserByRoleId(userIdList);
        if(CollectionUtils.isEmpty(selectedUserList)) {
            selectedUserList = Lists.<SysUser>newArrayList();
        }
        if(CollectionUtils.isEmpty(unSelectedUserList)) {
            unSelectedUserList = Lists.<SysUser>newArrayList();
        }
        map.put("selected", selectedUserList);
        map.put("unSelected", unSelectedUserList);
        return map;
    }
}
