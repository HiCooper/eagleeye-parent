package com.berry.eagleeye.auth.security.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author HiCooper
 * @since 2018-12-02
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户id获取角色
     *
     * @param userId 用户id
     * @return
     */
    Set<RoleInfo> getRolesByUserId(@Param("userId") Long userId);
}
