package com.paofeng.system.service;


import com.paofeng.system.api.domain.SysShop;

import java.util.List;

/**
 * 店铺 业务层
 *
 * @author ruoyi
 */
public interface ISysShopService {
    /**
     * 根据条件分页查询店铺列表
     *
     * @param shop 店铺信息
     * @return 店铺信息集合信息
     */
    List<SysShop> selectShopList(SysShop shop);

    /**
     * 通过店铺ID查询店铺
     *
     * @param shopId 店铺ID
     * @return 店铺对象信息
     */
    SysShop selectShopById(Long shopId);

    /**
     * 通过用户ID查询店铺
     *
     * @param shopId 店铺ID
     * @return 店铺对象信息
     */
    SysShop selectShopByUserId(Long userId);

    /**
     * 新增店铺信息
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int insertShop(SysShop shop);

    /**
     * 修改店铺信息
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int updateShop(SysShop shop);


    /**
     * 修改店铺状态
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int updateShopStatus(SysShop shop);

    /**
     * 修改店铺状态
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int updateShopByAdmin(SysShop shop);

}
