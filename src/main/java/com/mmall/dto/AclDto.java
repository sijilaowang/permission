package com.mmall.dto;

import com.mmall.model.SysAcl;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@ToString
public class AclDto extends SysAcl {

    //是否默认选择某个权限点
    private boolean checked = false;

    //是否可以操作这个权限
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl,dto);
        return dto;
    }
}
