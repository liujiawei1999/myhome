package com.jia.home.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiawei
 * @since 2022-03-23
 */
@TableName("t_corn_job")
public class CornJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;
    @TableField("beanName")
    private String beanName;
    @TableField("methodName")
    private String methodName;
    @TableField("methodParams")
    private String methodParams;
    @TableField("cornExpression")
    private String cornExpression;
    private Integer jobStatus;
    private String remark;
    private Date createTime;
    private Date updateTime;

    public CornJob() {
    }

    public CornJob(Integer jobId, String beanName, String methodName, String methodParams, String cornExpression, Integer jobStatus, String remark, Date createTime, Date updateTime) {
        this.jobId = jobId;
        this.beanName = beanName;
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.cornExpression = cornExpression;
        this.jobStatus = jobStatus;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(String methodParams) {
        this.methodParams = methodParams;
    }

    public String getCornExpression() {
        return cornExpression;
    }

    public void setCornExpression(String cornExpression) {
        this.cornExpression = cornExpression;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CornJob job = (CornJob) o;
        return Objects.equals(beanName, job.beanName) && Objects.equals(methodName, job.methodName) && Objects.equals(methodParams, job.methodParams) && Objects.equals(cornExpression, job.cornExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, methodName, methodParams, cornExpression);
    }

    @Override
    public String toString() {
        return "CornJob{" +
                "jobId=" + jobId +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodParams='" + methodParams + '\'' +
                ", cornExpression='" + cornExpression + '\'' +
                ", jobStatus=" + jobStatus +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
