package com.berry.eagleeye.bridge.remote.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/8 14:05
 * fileName：UserRoleDTO
 * Use：
 */
@Data
public class UserRoleDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
