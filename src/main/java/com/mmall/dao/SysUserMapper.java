package com.mmall.dao;

import com.mmall.beans.PageHelper;
import com.mmall.beans.PageVO;
import com.mmall.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    SysUser selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USER
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail,@Param("id") Long id);

    int countByTelephone(@Param("telephone") String telephone,@Param("id") Long id);

    List<SysUser> findAllSysUser();

    List<SysUser> findSysUserPage(@Param("dept_id") Long id,@Param("page") PageHelper pageHelper);

    List<SysUser> findAllSysUserPage(@Param("page") PageHelper pageHelper);

    int countUser(@Param("dept_id") Long deptId);

    SysUser findUserById(@Param("id") Long id);
}