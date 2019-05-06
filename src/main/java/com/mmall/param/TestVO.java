package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class TestVO {
    @NotBlank
    private String msg;
    @NotBlank
    private Integer id;

}
