package com.berry.eagleeye.auth.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.berry.eagleeye.auth.dao.entity.AccessKeyInfo;
import com.berry.eagleeye.auth.module.dto.UserInfoDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author HiCooper
 * @since 2019-06-26
 */
public interface IAccessKeyInfoDaoService extends IService<AccessKeyInfo> {

    UserInfoDTO getUserInfoDTO(String accessKeyId);
}
