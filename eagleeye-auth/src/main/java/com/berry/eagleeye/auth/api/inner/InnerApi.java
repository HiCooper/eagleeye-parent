package com.berry.eagleeye.auth.api.inner;

import com.berry.eagleeye.auth.dao.service.IAccessKeyInfoDaoService;
import com.berry.eagleeye.auth.module.dto.UserInfoDTO;
import com.berry.eagleeye.auth.module.dto.UserRoleDTO;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.service.IUserDaoService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/8 15:21
 * fileName：InnerApi
 * Use：内部调用 api
 */
@Api(tags = "内部服务调用")
@RestController
@RequestMapping("/api/inner")
@PreAuthorize("hasRole('ROLE_INNER_SERVICE')")
public class InnerApi {

    @Autowired
    private IUserDaoService userDaoService;

    @Autowired
    private IAccessKeyInfoDaoService accessKeyInfoDaoService;

    /**
     * 根据用户ID 获取该用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    @GetMapping("/v1/find_role_list")
    public Set<UserRoleDTO> findRoleListByUserId(@RequestParam("userId") Long userId) {
        Set<RoleInfo> roleInfoSet = userDaoService.findRoleListByUserId(userId);
        if (CollectionUtils.isEmpty(roleInfoSet)) {
            return new HashSet<>();
        }
        return roleInfoSet.stream().map(role -> {
            UserRoleDTO dto = new UserRoleDTO();
            BeanUtils.copyProperties(role, dto);
            return dto;
        }).collect(Collectors.toSet());
    }


    /**
     * 根据 SDK 密钥key 查找用户
     *
     * @param accessKeyId 密钥key
     * @return 用户信息
     */
    @GetMapping("/v1/get_user_info")
    public UserInfoDTO getUserInfoDTO(@RequestParam("accessKeyId") String accessKeyId) {
        return accessKeyInfoDaoService.getUserInfoDTO(accessKeyId);
    }
}
