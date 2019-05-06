package com.mmall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 解决mybatis oracle 数据库 主键不自增问题
 */

public interface SequenceGeneratorMapper {
    Integer nextIntValue(@Param("sequenceName") String sequenceName);
    Long nextLongValue(@Param("sequenceName")String sequenceName);
    String nextStringValue(@Param("sequenceName")String sequenceName);
}
