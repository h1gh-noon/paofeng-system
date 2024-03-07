package com.paofeng.system.service.impl;

import com.paofeng.common.core.enums.SysRoles;
import com.paofeng.common.security.service.TokenService;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.system.api.domain.SysShop;
import com.paofeng.system.api.domain.SysUser;
import com.paofeng.system.api.model.LoginUser;
import com.paofeng.system.mapper.SysShopMapper;
import com.paofeng.system.service.ISysPermissionService;
import com.paofeng.system.service.ISysRoleService;
import com.paofeng.system.service.ISysShopService;
import com.paofeng.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysShopServiceImpl implements ISysShopService {

    @Resource
    private SysShopMapper shopMapper;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private TokenService tokenService;

    @Resource
    private ISysPermissionService permissionService;

    @Resource
    private ISysUserService userService;

    @Override
    public List<SysShop> selectShopList(SysShop shop) {
        return shopMapper.selectShopList(shop);
    }

    @Override
    public SysShop selectShopById(Long shopId) {
        return shopMapper.selectShopById(shopId);
    }

    @Override
    public SysShop selectShopByUserId(Long userId) {
        return shopMapper.selectShopByUserId(userId);
    }

    @Override
    public int insertShop(SysShop shop) {
        // 如果没有shop角色就添加一个
        if (SecurityUtils.getLoginUser().getRoles().stream().noneMatch(e -> e.equals(SysRoles.SHOP.getName()))) {
            roleService.insertAuthUsers(SysRoles.SHOP.getId(), new Long[]{shop.getUserId()});
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getRoles().add(SysRoles.SHOP.getName());
            SysUser sysUser = userService.selectUserByUserName(loginUser.getUsername());
            loginUser.setPermissions(permissionService.getMenuPermission(sysUser));
            // 刷新redis
            tokenService.setLoginUser(loginUser);
        }

        return shopMapper.insertShop(shop);
    }

    @Override
    public int updateShop(SysShop shop) {
        return shopMapper.updateShop(shop);
    }

    @Override
    public int updateShopStatus(SysShop shop) {
        return shopMapper.updateShopStatus(shop);
    }

    @Override
    public int updateShopByAdmin(SysShop shop) {
        return shopMapper.updateShop(shop);
    }
}
