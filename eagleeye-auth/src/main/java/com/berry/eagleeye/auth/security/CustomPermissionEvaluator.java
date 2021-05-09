package com.berry.eagleeye.auth.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.entity.SysPermission;
import com.berry.eagleeye.auth.security.dao.service.IRoleDaoService;
import com.berry.eagleeye.auth.security.dao.service.ISysPermissionDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/15 11:29
 * fileName：CustomPermissionEvaluator
 * Use：
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    private final ISysPermissionDaoService sysPermissionDaoService;

    private final IRoleDaoService roleDaoService;

    public CustomPermissionEvaluator(ISysPermissionDaoService sysPermissionDaoService, IRoleDaoService roleDaoService) {
        this.sysPermissionDaoService = sysPermissionDaoService;
        this.roleDaoService = roleDaoService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        logger.info("check hasPermission...");
        List<String> roleNames = authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
        // 根据角色名列表 获取角色id列表
        List<RoleInfo> roleInfos = roleDaoService.list(new QueryWrapper<RoleInfo>().in("name", roleNames));
        if (CollectionUtils.isEmpty(roleInfos)) {
            return false;
        }
        List<Long> roleIds = roleInfos.stream().map(RoleInfo::getId).collect(Collectors.toList());

        // 根据角色id列表 获取权限列表
        List<SysPermission> sysPermissions = sysPermissionDaoService.list(new QueryWrapper<SysPermission>().in("role_id", roleIds));
        if (CollectionUtils.isEmpty(sysPermissions)) {
            return false;
        }
        return sysPermissions.stream().anyMatch(item -> {
            List<String> permissions = item.getPermissions();
            if (CollectionUtils.isEmpty(permissions)) {
                return false;
            }
            return item.getUrl().equalsIgnoreCase(targetUrl.toString()) && permissions.contains(permission.toString());
        });
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        return false;
    }
}
