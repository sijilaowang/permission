package com.mmall.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AclModuleParam {

    private Long id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(min = 2,max = 20,message = "权限模块名称字数在2个字以上,20个字以下")
    private String name;

    private Long parentId = 0L;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Long seq;

    @NotNull(message = "权限模块状态不能为空")
    @Min(value = 0,message = "权限模块状态不合法")
    @Max(value = 1,message = "权限模块状态不合法")
    private Short status;

    @Length(max = 200, message = "权限模块备注字数在200个字以内")
    private String remark;
}
