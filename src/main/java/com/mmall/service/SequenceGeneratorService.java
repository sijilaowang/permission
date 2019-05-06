package com.mmall.service;

import com.mmall.dao.SequenceGeneratorMapper;
import com.mmall.exception.ParamExcepiton;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SequenceGeneratorService {
    @Resource
    SequenceGeneratorMapper sequenceGeneratorMapper;

    public Integer nextIntValue(String sequenceName) {
        return sequenceGeneratorMapper.nextIntValue(sequenceName);
    }

    public Long nextLongValue(String sequenceName) {
        return sequenceGeneratorMapper.nextLongValue(sequenceName);
    }

    public String nextStringValue(String sequenceName) {
        return sequenceGeneratorMapper.nextStringValue(sequenceName);
    }

}
