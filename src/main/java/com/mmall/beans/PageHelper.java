package com.mmall.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

public class PageHelper {

    @Getter
    @Setter
    @Min(value = 1,message = "显示条数不合法")
    private Integer pageSize = 10;

    @Getter
    @Setter
    @Min(value = 1,message = "页码错误")
    private Integer pageNo = 1;

    @Setter
    private Integer startRow;

    @Setter
    private Integer endRow;

    public Integer getStartRow() {
        return (pageNo - 1) * pageSize;
    }

    public Integer getEndRow() {
        return pageNo * pageSize;
    }
}
