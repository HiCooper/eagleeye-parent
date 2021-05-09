package com.berry.eagleeye.auth.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.berry.eagleeye.auth.dao.entity.AccessKeyInfo;
import com.berry.eagleeye.auth.dao.mapper.AccessKeyInfoMapper;
import com.berry.eagleeye.auth.dao.service.IAccessKeyInfoDaoService;
import com.berry.eagleeye.auth.module.dto.UserInfoDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HiCooper
 * @since 2019-06-26
 */
@Service
public class AccessKeyInfoDaoServiceImpl extends ServiceImpl<AccessKeyInfoMapper, AccessKeyInfo> implements IAccessKeyInfoDaoService {

    @Resource
    private AccessKeyInfoMapper accessKeyInfoMapper;

    @Override
    public UserInfoDTO getUserInfoDTO(String accessKeyId) {
        return accessKeyInfoMapper.getUserInfoDTO(accessKeyId);
    }
}
