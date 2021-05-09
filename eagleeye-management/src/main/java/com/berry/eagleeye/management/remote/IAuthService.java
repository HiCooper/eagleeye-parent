package com.berry.eagleeye.management.remote;

import com.berry.eagleeye.management.remote.dto.UserRoleDTO;
import com.berry.eagleeye.management.security.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/8 14:03
 * fileName：IAuthService
 * Use：
 */
@FeignClient(name = "eagleeye-auth")
public interface IAuthService {

    /**
     * 根据用户ID 获取该用户的角色列表
     * @param userId 用户ID
     * @return 角色集合
     */
    @GetMapping("/api/auth/inner/find_role_list")
    Set<UserRoleDTO> findRoleListByUserId(Long userId);

    /**
     * 根据 SDK 密钥key 查找用户
     * @param accessKeyId 密钥key
     * @return 用户信息
     */
    @GetMapping("/api/auth/inner/get_user_info")
    UserInfoDTO getUserInfoDTO(String accessKeyId);
}
