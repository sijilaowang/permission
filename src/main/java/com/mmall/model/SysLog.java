package com.mmall.model;

import java.util.Date;

public class SysLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.ID
     *
     * @mbggenerated
     */
    private Short id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.TYPE
     *
     * @mbggenerated
     */
    private Short type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.TARGET_ID
     *
     * @mbggenerated
     */
    private Short targetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.OPERATOR
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.OPERATOR_TIME
     *
     * @mbggenerated
     */
    private Date operatorTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.OPERATOR_IP
     *
     * @mbggenerated
     */
    private String operatorIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_LOG.STATUS
     *
     * @mbggenerated
     */
    private Short status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.ID
     *
     * @return the value of SYS_LOG.ID
     *
     * @mbggenerated
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.ID
     *
     * @param id the value for SYS_LOG.ID
     *
     * @mbggenerated
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.TYPE
     *
     * @return the value of SYS_LOG.TYPE
     *
     * @mbggenerated
     */
    public Short getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.TYPE
     *
     * @param type the value for SYS_LOG.TYPE
     *
     * @mbggenerated
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.TARGET_ID
     *
     * @return the value of SYS_LOG.TARGET_ID
     *
     * @mbggenerated
     */
    public Short getTargetId() {
        return targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.TARGET_ID
     *
     * @param targetId the value for SYS_LOG.TARGET_ID
     *
     * @mbggenerated
     */
    public void setTargetId(Short targetId) {
        this.targetId = targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.OPERATOR
     *
     * @return the value of SYS_LOG.OPERATOR
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.OPERATOR
     *
     * @param operator the value for SYS_LOG.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.OPERATOR_TIME
     *
     * @return the value of SYS_LOG.OPERATOR_TIME
     *
     * @mbggenerated
     */
    public Date getOperatorTime() {
        return operatorTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.OPERATOR_TIME
     *
     * @param operatorTime the value for SYS_LOG.OPERATOR_TIME
     *
     * @mbggenerated
     */
    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.OPERATOR_IP
     *
     * @return the value of SYS_LOG.OPERATOR_IP
     *
     * @mbggenerated
     */
    public String getOperatorIp() {
        return operatorIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.OPERATOR_IP
     *
     * @param operatorIp the value for SYS_LOG.OPERATOR_IP
     *
     * @mbggenerated
     */
    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp == null ? null : operatorIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_LOG.STATUS
     *
     * @return the value of SYS_LOG.STATUS
     *
     * @mbggenerated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_LOG.STATUS
     *
     * @param status the value for SYS_LOG.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(Short status) {
        this.status = status;
    }
}