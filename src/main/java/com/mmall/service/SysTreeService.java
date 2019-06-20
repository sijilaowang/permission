package com.mmall.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.AclDto;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.security.acl.Acl;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class SysTreeService<T> {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysCoreService sysCoreService;

    @Resource
    private SysAclMapper sysAclMapper;

    public List<AclModuleLevelDto> roleTree(Long roleId) {
        //取出所有权限点
        List<SysAcl> allAclList = sysAclMapper.findAll();
        //取出用户已经分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //取出角色已经分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        //将用户取出来的所有权限点,取出里面的id构成set
        Set<Long> userAclIdSet = userAclList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        //取出角色分配的权限点,
        Set<Long> roleAclIdSet = roleAclList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        //因为用户的权限点和角色的权限点会有重复,所以这些取并集
        Set<SysAcl> aclSet = new HashSet<>(roleAclList);
        aclSet.addAll(userAclList);
        List<AclDto>  aclDtoList = Lists.newArrayList();
        //遍历整个权限集合
        for(SysAcl sysAcl : allAclList) {
            AclDto dto = AclDto.adapt(sysAcl);
            //判断用户里的权限点是否包含并集里的id,
            if(userAclIdSet.contains(sysAcl.getId())) {
                dto.setHasAcl(true);
            }
            if(roleAclIdSet.contains(sysAcl.getId())) {
                //有就设置这权限是选中
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        //返回的样例
        //{"ret":true,"msg":null,"data":[{"id":1,"name":"查询","parentId":0,"levels":"0","seq":0,"status":1,"remark":"查询权限","operator":"admin","operatorTime":1556576565000,"operatorIp":"127.0.0.1","sysAclModuleList":[{"id":44,"name":"查询管理","parentId":1,"levels":"0.1","seq":0,"status":1,"remark":"2","operator":"admin","operatorTime":1558337051000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[{"id":7,"name":"查询用户","parentId":44,"levels":"0.1.44","seq":1,"status":1,"remark":"查询用户","operator":"admin","operatorTime":1557045689000,"operatorIp":"127.0.0.1","sysAclModuleList":[],"aclDtoList":[]}],"aclDtoList":[]}],"aclDtoList":[{"id":1,"code":"query","name":"查询下级接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":true,"hasAcl":true},{"id":2,"code":"query","name":"查询上级级接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":3,"code":"query","name":"查询中级接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":4,"code":"query","name":"查询外部接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":5,"code":"query","name":"查询内部接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":6,"code":"query","name":"查询一类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":7,"code":"query","name":"查二类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":true,"hasAcl":true},{"id":8,"code":"query","name":"查三类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":true,"hasAcl":true},{"id":9,"code":"query","name":"查四类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":10,"code":"query","name":"查五类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":11,"code":"query","name":"查六类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":12,"code":"query","name":"查七类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":13,"code":"query","name":"查八类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true},{"id":14,"code":"query","name":"查九类接口","aclModuleId":1,"url":"/query","type":3,"status":1,"seq":1,"remark":null,"operator":"admin","operatorTime":1560161710000,"operatorIp":"127.0.0.1","sysAclModule":null,"checked":false,"hasAcl":true}]},{"id":2,"name":"修改","parentId":0,"levels":"0","seq":0,"status":1,"remark":"修改权限","operator":"admin","operatorTime":1556576631000,"operatorIp":"127.0.0.1","sysAclModuleList":[{"id":5,"name":"修改用户","parentId":2,"levels":"0.2","seq":1,"status":1,"remark":"修改用户","operator":"admin","operatorTime":1557044212000,"operatorIp":"127.0.0.1","sysAclModuleList":[],"aclDtoList":[]}],"aclDtoList":[]},{"id":3,"name":"增加","parentId":0,"levels":"0","seq":0,"status":1,"remark":"增加权限","operator":"admin","operatorTime":1556576659000,"operatorIp":"127.0.0.1","sysAclModuleList":[{"id":6,"name":"增加用户","parentId":3,"levels":"0.3","seq":1,"status":1,"remark":"增加用户","operator":"admin","operatorTime":1557044375000,"operatorIp":"127.0.0.1","sysAclModuleList":[],"aclDtoList":[]}],"aclDtoList":[]},{"id":22,"name":"公共管理","parentId":0,"levels":"0","seq":0,"status":1,"remark":"公共管理","operator":"admin","operatorTime":1558073592000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[{"id":23,"name":"设备管理","parentId":22,"levels":"0.22","seq":0,"status":1,"remark":"设备管理","operator":"admin","operatorTime":1558073616000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[],"aclDtoList":[]}],"aclDtoList":[]},{"id":26,"name":"餐饮管理","parentId":0,"levels":"0","seq":0,"status":1,"remark":"餐饮管理","operator":"admin","operatorTime":1558077818000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[],"aclDtoList":[]},{"id":27,"name":"显示管理","parentId":0,"levels":"0","seq":0,"status":1,"remark":"显示管理","operator":"admin","operatorTime":1558078217000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[{"id":63,"name":"显示中文","parentId":27,"levels":"0.27","seq":0,"status":1,"remark":"chinese","operator":"admin","operatorTime":1560182245000,"operatorIp":"0:0:0:0:0:0:0:1","sysAclModuleList":[],"aclDtoList":[]}],"aclDtoList":[]}]}
        return aclListToTree(aclDtoList);
    }

    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if(CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();
        Multimap<Long,AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            //处理有效的权限点
            if(acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId().longValue(),acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList,moduleIdAclMap);
        return aclModuleLevelList;
    }

    //因为这里渲染权限树的时候,是要全部显示,然后是默认勾选有权限的,所以上面方法遍历的时候,要取所有权限点
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList,Multimap<Long,AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for(AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if(CollectionUtils.isNotEmpty(aclDtoList)) {
                //排序
                aclDtoList.sort((AclDto o1,AclDto o2) -> o1.getSeq().compareTo(o2.getSeq()));
                dto.setAclDtoList(aclDtoList);
            }
            bindAclsWithOrder(dto.getSysAclModuleList(),moduleIdAclMap);
        }
    }

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
