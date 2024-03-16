package com.paofeng.system.service.impl;

import com.paofeng.common.core.enums.SysRoles;
import com.paofeng.system.api.domain.SysShop;
import com.paofeng.system.mapper.SysShopMapper;
import com.paofeng.system.service.ISysRoleService;
import com.paofeng.system.service.ISysShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysShopServiceImpl implements ISysShopService {

    @Resource
    private SysShopMapper shopMapper;

    @Resource
    private ISysRoleService roleService;

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
        return shopMapper.insertShop(shop);
    }

    @Override
    public int updateShop(SysShop shop) {
        return shopMapper.updateShop(shop);
    }

    @Override
    @Transactional
    public int updateShopStatus(SysShop shop) {
        if (SysShop.STATUS_ENABLE.equals(shop.getStatus())) { // 启用
            roleService.insertAuthUsers(SysRoles.SHOP.getId(), new Long[]{shop.getUserId()});
        } else {
            roleService.deleteAuthUsers(SysRoles.SHOP.getId(), new Long[]{shop.getUserId()});
        }
        return shopMapper.updateShopStatus(shop);
    }

    @Override
    public int updateShopByAdmin(SysShop shop) {
        return shopMapper.updateShop(shop);
    }
}
