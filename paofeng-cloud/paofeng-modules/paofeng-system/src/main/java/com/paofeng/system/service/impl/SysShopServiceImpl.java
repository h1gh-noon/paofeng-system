package com.paofeng.system.service.impl;

import com.paofeng.system.domain.SysShop;
import com.paofeng.system.mapper.SysShopMapper;
import com.paofeng.system.service.ISysShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysShopServiceImpl implements ISysShopService {

    @Resource
    private SysShopMapper shopMapper;

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
        return null;
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
    public int updateShopStatus(SysShop shop) {
        return shopMapper.updateShopStatus(shop);
    }

    @Override
    public int updateShopByAdmin(SysShop shop) {
        return shopMapper.updateShop(shop);
    }
}
