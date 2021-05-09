package com.berry.eagleeye.bridge.remote;

import com.berry.eagleeye.bridge.remote.client.AuthorizedFeignClient;
import com.berry.eagleeye.bridge.remote.dto.UserRoleDTO;
import com.berry.eagleeye.bridge.security.dto.UserInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/8 14:03
 * fileName：IAuthService
 * Use：Rest ful 远程调用 auth API
 */
@AuthorizedFeignClient(name = "eagleeye-auth")
public interface IAuthService {

    /**
     * 根据用户ID 获取该用户的角色列表
     * @param userId 用户ID
     * @return 角色集合
     */
    @GetMapping("/api/inner/v1/find_role_list")
    Set<UserRoleDTO> findRoleListByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据 SDK 密钥key 查找用户
     * @param accessKeyId 密钥key
     * @return 用户信息
     */
    @GetMapping("/api/inner/v1/get_user_info")
    UserInfoDTO getUserInfoDTO(@RequestParam("accessKeyId")  String accessKeyId);
}
