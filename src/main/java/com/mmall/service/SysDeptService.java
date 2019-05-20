package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.common.Sequence;
import com.mmall.dao.SequenceGeneratorMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamExcepiton;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import sun.misc.Request;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SequenceGeneratorMapper sequenceGenerator;



    public void save(DeptParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级下存在相同名称的部门");
        }
        //TODO 这里id和parentId传入错误,计算出来的等级也是错误的
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        dept.setLevels(LevelUtil.calculateLevel(getLevel(dept.getParentId()),dept.getParentId()));
        dept.setId(sequenceGenerator.nextLongValue(Sequence.SYS_DEPT_ID_SEQ));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());//获取当前线程中的用户
        dept.setOperatorTime(new Date());
        dept.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));//TODO
        sysDeptMapper.insertSelective(dept);
    }

    public void update(DeptParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamExcepiton("同一层级下存在相同名称的部门");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevels(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());//TODO
        after.setOperatorTime(new Date());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));//TODO
        updateWithChild(before,after);
    }

    //@Transactional
    private void updateWithChild(SysDept before,SysDept after) {
        String newLevelPrefix = after.getLevels();
        String oldLevelPrefix = before.getLevels();
        if (!after.getLevels().equals(before.getLevels())) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevels());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevels();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevels(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }
    private boolean checkExist(Long parentId, String deptName, Long deptId) {
        //判断数据库中存不存在这条记录,新增的时候deptId传空
        return sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId) > 0;
    }

    private String getLevel(Long deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept == null) {
            return null;
        }
        return dept.getLevels();
    }
}
