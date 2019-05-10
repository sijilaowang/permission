package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysAclModule;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;
@Data
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> sysAclModuleList = Lists.newArrayList();

    /**
     * 将SysAclModule类型数据转成成AclModuleLevelDto 类型
     * @param sysAclModule
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        if(null == sysAclModule) {
            return null;
        }
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule,dto);
        return dto;
    }
}
