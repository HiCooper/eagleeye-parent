package com.berry.eagleeye.auth.module.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/15 13:58
 * fileName：RolePermissionListVo
 * Use：
 */
@Data
public class RolePermissionListVo {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 资源url
     */
    private String url;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限
     */
    private String permission;
}
