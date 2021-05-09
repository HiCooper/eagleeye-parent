package com.berry.eagleeye.auth.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018-12-02 23:27
 * fileName：UserDTO
 * Use：
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 激活状态
     */
    private Boolean activated;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次修改时间
     */
    private Date updateTime;

    @JsonIgnore
    private Set<RoleInfo> roleInfoSet;
}
