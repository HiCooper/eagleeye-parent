package com.berry.eagleeye.auth.security.dao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author HiCooper
 * @since 2018-12-02
 */
public interface IRoleDaoService extends IService<RoleInfo> {
    List<RolePermissionListVo> pageListRolePermission(IPage<RolePermissionListVo> page, String roleId, String url, String permission);
}
