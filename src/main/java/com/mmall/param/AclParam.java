package com.mmall.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AclParam {

    private Long id;

    @NotBlank(message = "权限点名称不可以为空")
    @Length(min = 2,max = 64,message = "权限点名称需要在2-64字符内")
    private String name;

    @NotNull(message = "必须指定权限模块")
    private Long aclModuleId;

    @Length(min = 6,max = 256,message = "权限点名称需要在6-256")
    private String url;

    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 0,message = "权限点类型不合法")
    @Max(value = 2,message = "权限点类型不合法")
    private Short type;

    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0,message = "权限点类型不合法")
    @Max(value = 1,message = "权限点类型不合法")
    private Short status;

    @NotNull(message = "必须指定权限点的展示顺序")
    private Short seq;

    @Length(min = 0,max = 256,message = "权限点名称需要在0-256")
    private String remark;
}

