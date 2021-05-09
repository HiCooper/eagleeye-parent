package com.berry.eagleeye.auth.api.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.berry.eagleeye.auth.common.Result;
import com.berry.eagleeye.auth.common.ResultFactory;
import com.berry.eagleeye.auth.module.vo.RolePermissionListVo;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/12 9:10
 * fileName：RoleManagementController
 * Use：角色管理
 */
@RestController
@RequestMapping("/management/role")
@Api(tags = "角色管理")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleManagementController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation("分页获取角色列表")
    @GetMapping(value = "/page_list")
    public Result pageListRole(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam String roleId
    ) {
        IPage<RoleInfo> rolePage = roleService.pageListRole(pageSize, pageNum, roleId);
        return ResultFactory.wrapper(rolePage);
    }

    @ApiOperation("角色详情")
    @GetMapping(value = "/detail")
    public Result detailRole() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("创建角色")
    @PostMapping(value = "/create")
    public Result createRole() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("删除角色")
    @PostMapping(value = "/delete")
    public Result deleteRole() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/edit")
    public Result editRole() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("分页获取角色权限列表")
    @GetMapping(value = "/page_permission")
    public Result<IPage<RolePermissionListVo>> pageListRolePermission(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String permission) {
        IPage<RolePermissionListVo> page = new Page<>(pageNum, pageSize);
        List<RolePermissionListVo> voList = roleService.pageListRolePermission(page, roleId, url, permission);
        page.setRecords(voList);
        return ResultFactory.wrapper(page);
    }

}
