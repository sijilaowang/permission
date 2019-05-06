package com.mmall.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept: deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     * 返回所有部门中的根部门
     * @param dtoList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> dtoList) {
        if(CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String,DeptLevelDto> dtoMultimap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : dtoList) {
            dtoMultimap.put(dto.getLevels(),dto);
            if(LevelUtil.ROOT.equals(dto.getLevels())) {
                rootList.add(dto);
            }
        }
        rootList.sort((DeptLevelDto o1,DeptLevelDto o2) -> o1.getSeq().compareTo(o2.getSeq()));
        recursionDeptList(rootList,LevelUtil.ROOT,dtoMultimap);
        return rootList;
    }

    public void recursionDeptList(List<DeptLevelDto> deptLevelDto,String level,Multimap<String,DeptLevelDto> deptLevelMultimp) {
        for(int i=0;i<deptLevelDto.size();i++) {
            DeptLevelDto deptDto = deptLevelDto.get(i);
            //计算下一层级的值
            String nextLevel = LevelUtil.calculateLevel(deptDto.getLevels(),deptDto.getId());
            //在map中取出层级列表
            List<DeptLevelDto> deptLevelDtos = (List<DeptLevelDto>) deptLevelMultimp.get(nextLevel);
            if(CollectionUtils.isNotEmpty(deptLevelDtos)) {
                deptLevelDtos.sort((DeptLevelDto o1,DeptLevelDto o2) -> o1.getSeq().compareTo(o2.getSeq()));
                deptDto.setDeptList(deptLevelDtos);
                recursionDeptList(deptLevelDtos, nextLevel,deptLevelMultimp);
            }
        }
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelDtoList,String level,Multimap<String,DeptLevelDto> levelDeptMap) {
        for(int i=0;i<deptLevelDtoList.size();i++) {
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(deptLevelDto.getLevels(),deptLevelDto.getId());
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempDeptList)) {
                tempDeptList.sort((DeptLevelDto o1,DeptLevelDto o2) -> o1.getSeq().compareTo(o2.getSeq()));
                deptLevelDto.setDeptList(tempDeptList);
                transformDeptTree(tempDeptList,nextLevel,levelDeptMap);
            }
        }
    }
}
