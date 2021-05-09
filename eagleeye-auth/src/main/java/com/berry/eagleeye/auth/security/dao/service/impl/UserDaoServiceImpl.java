package com.berry.eagleeye.auth.security.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.entity.UserInfo;
import com.berry.eagleeye.auth.security.dao.mapper.UserMapper;
import com.berry.eagleeye.auth.security.dao.service.IUserDaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HiCooper
 * @since 2018-12-02
 */
@Service
public class UserDaoServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserDaoService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Optional<UserInfo> findOneByUsername(String lowercaseLogin) {
        return Optional.ofNullable(userMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", lowercaseLogin)));
    }

    @Override
    public Set<RoleInfo> findRoleListByUserId(Long userId) {
        return userMapper.getRolesByUserId(userId);
    }
}
