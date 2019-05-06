package com.mmall.param;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class DeptParam {
    private Long id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max=15,min=2,message = "部门名称要求在2-15个字之间")
    private String name;

    //如果parentId为空,默认为最高级菜单,parentId设置为0
    private Long parentId = 0L;

    @NotNull(message = "展示顺序不可以为空")
    private Long seq;

    @Length(max=150,message = "备注的长度需要在150个字以内")
    private String remark;
}
