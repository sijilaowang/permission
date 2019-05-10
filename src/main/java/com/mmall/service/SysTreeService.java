package com.mmall.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysAclModule;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.security.acl.Acl;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Service
public class SysTreeService<T> {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleService sysAclModuleService;

    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> list = sysAclModuleService.findAll();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for(SysAclModule sysAclModule : list) {
            AclModuleLevelDto dto = AclModuleLevelDto.adapt(sysAclModule);
            dtoList.add(dto);
        }
        return aclModuleListToTree(dtoList);
    }

    public List<T> TListToTree(List<T> list,Class<T> clazz) throws Exception {
        String className = clazz.getSimpleName();
        Method getLevel = clazz.getMethod("getLevel");
        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        Multimap<String,T> multimap = ArrayListMultimap.create();
        List<T> tList = Lists.newArrayList();
        for(T t : list) {
            String result = (String) getLevel.invoke(clazz.newInstance());
            multimap.put(result,t);
            if(LevelUtil.ROOT.equals(t.getClass().getMethod("getLevel").invoke(t.getClass().newInstance()))) {
                tList.add(t);
            }
        }
        return tList;
    }

    /**
     * 权限模块数据转成树格式 List[list,list[]...] List<Map<String,Object>>
     * multimap格式样例 level:0,data {[查询...],[修改...],[增加...]},level 0.1,data{[查询xxx...]}
     * @param list
     * @return
     */
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> list) {
        //为空返回空list
        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        Multimap<String,AclModuleLevelDto> multimap = ArrayListMultimap.create();
        List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();
        for(AclModuleLevelDto aclModuleLevelDto : list) {
            multimap.put(aclModuleLevelDto.getLevels(), aclModuleLevelDto);
            if(LevelUtil.ROOT.equals(aclModuleLevelDto.getLevels())) {
                aclModuleList.add(aclModuleLevelDto);
            }
        }
        recurAclModuleList(aclModuleList,multimap);
        return aclModuleList;
    }

    /**
     * 递归处理
     * @param
     */
    public void recurAclModuleList(List<AclModuleLevelDto> aclModuleLevelDtoList,Multimap<String,AclModuleLevelDto> multimap) {
        for(AclModuleLevelDto aclModuleLevelDto : aclModuleLevelDtoList) {
            //计算下一个层级,根据自身层级和id 计算出下一层级level
            String nextLevel = LevelUtil.calculateLevel(aclModuleLevelDto.getLevels(),aclModuleLevelDto.getId());
            //取出下一层级的list
            List<AclModuleLevelDto> aclModuleLevelDtos = (List<AclModuleLevelDto>) multimap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(aclModuleLevelDtos)) {
                //将下一层级的list存入到父节点中
                aclModuleLevelDto.setSysAclModuleList(aclModuleLevelDtos);
                aclModuleLevelDtos.sort((AclModuleLevelDto a1,AclModuleLevelDto a2) -> a1.getSeq().compareTo(a2.getSeq()));
                //判断下一层级的list有没有子节点
                recurAclModuleList(aclModuleLevelDtos,multimap);
            }

        }
    }


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
