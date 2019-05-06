package com.mmall.param;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ToString
public class RoleParam {

    private Long id;

    @NotBlank(message = "角色名不可以为空")
    @Length(min = 1,max = 20, message = "用户名长度需要在20个字以内")
    private String name;

    @Min(value = 0,message = "用户状态不合法")
    @Max(value = 1,message = "用户状态不合法")
    private Short status = 1;

    @Length(min = 0,max = 200,message = "备注长度需要在200个字以内")
    private String remark;
}
