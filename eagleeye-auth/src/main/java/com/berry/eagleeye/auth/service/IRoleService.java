package com.berry.eagleeye.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/15 14:00
 * fileName：IRoleService
 * Use：
 */
public interface IRoleService {
    /**
     * 分页获取角色权限列表
     *
     * @param page       分页参数
     * @param roleId     角色id
     * @param url        资源url
     * @param permission 权限
     * @return 分页列表
     */
    List<RolePermissionListVo> pageListRolePermission(IPage<RolePermissionListVo> page, String roleId, String url, String permission);

    /**
     * 分页获取角色列表
     *
     * @param pageSize 分页代销
     * @param pageNum  当前页
     * @param roleId
     * @return page list
     */
    IPage<RoleInfo> pageListRole(Integer pageSize, Integer pageNum, String roleId);
}
