package com.mmall.dao;

import com.mmall.beans.PageHelper;
import com.mmall.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    int insert(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    int insertSelective(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    SysAcl selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysAcl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_ACL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysAcl record);

    List<SysAcl> findAll();

    int countSysAclByNameAndId(@Param("name") String name,@Param("aclModuleId") Long aclModuleId);

    List<SysAcl> selectByAclModuleId(@Param("aclModuleId") Long aclModuleId, @Param("page") PageHelper pageHelper);

    int countAclById(Long aclModuleId);

    List<SysAcl> getByIdList(@Param("idList") List<Long> idList);
}