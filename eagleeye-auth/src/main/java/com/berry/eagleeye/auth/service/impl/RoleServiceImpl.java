package com.berry.eagleeye.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.service.IRoleDaoService;
import com.berry.eagleeye.auth.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/15 14:01
 * fileName：RoleServiceImpl
 * Use：
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDaoService roleDaoService;

    @Override
    public List<RolePermissionListVo> pageListRolePermission(IPage<RolePermissionListVo> page, String roleId, String url, String permission) {
        return roleDaoService.pageListRolePermission(page, roleId, url, permission);
    }

    @Override
    public IPage<RoleInfo> pageListRole(Integer pageSize, Integer pageNum, String roleId) {
        return roleDaoService.page(new Page<>(pageNum, pageSize), new QueryWrapper<RoleInfo>().eq("id", roleId));
    }
}
