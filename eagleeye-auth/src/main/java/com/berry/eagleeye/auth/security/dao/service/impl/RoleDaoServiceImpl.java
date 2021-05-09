package com.berry.eagleeye.auth.security.dao.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.mapper.RoleMapper;
import com.berry.eagleeye.auth.security.dao.service.IRoleDaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HiCooper
 * @since 2018-12-02
 */
@Service
public class RoleDaoServiceImpl extends ServiceImpl<RoleMapper, RoleInfo> implements IRoleDaoService {

    @Resource
    private RoleMapper mapper;

    @Override
    public List<RolePermissionListVo> pageListRolePermission(IPage<RolePermissionListVo> page, String roleId, String url, String permission) {
        return mapper.pageListRolePermission(page, roleId, url, permission);
    }
}
