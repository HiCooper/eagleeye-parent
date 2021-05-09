package com.berry.eagleeye.bridge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 处理记录
 * </p>
 *
 * @author HiCooper
 * @since 2020-12-29
 */
@Data
public class RecordDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 所属用户ID
     */
    private String appName;

    /**
     * 记录处理进度状态
     */
    private String processStatus;

    /**
     * 请求记录ID
     */
    private String requestRecordId;

    /**
     * 请求版本
     */
    private String requestVersion;

    /**
     * 事项编码
     */
    private String sid;

    /**
     * 调用模式
     */
    private String calcMode;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 消息路由
     */
    private String routingKey;

    /**
     * 回调通知次数
     */
    private Integer noticeTimes;

    /**
     * 回调通知状态（INIT/SUCCESS/PROCESSING/FAIL）
     */
    private String noticeStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
