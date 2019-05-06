package com.mmall.beans;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {

    private List<T> list = Lists.newArrayList();

    private int total = 0;
}
