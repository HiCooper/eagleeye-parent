package com.berry.eagleeye.auth.security.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author HiCooper
 * @since 2018-12-02
 */
public interface RoleMapper extends BaseMapper<RoleInfo> {
    List<RolePermissionListVo> pageListRolePermission(IPage<RolePermissionListVo> page,
                                                      @Param("roleId") String roleId,
                                                      @Param("url") String url,
                                                      @Param("permission")String permission);
}
