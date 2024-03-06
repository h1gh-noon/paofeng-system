package com.paofeng.system.mapper;

import com.paofeng.system.api.domain.SysShop;

import java.util.List;

/**
 * 店铺表 数据层
 *
 * @author ruoyi
 */
public interface SysShopMapper {
    /**
     * 根据条件分页查询店铺列表
     *
     * @param sysShop 店铺信息
     * @return 店铺信息集合信息
     */
    List<SysShop> selectShopList(SysShop sysShop);

    /**
     * 通过店铺ID查询店铺
     *
     * @param shopId 店铺ID
     * @return 店铺对象信息
     */
    SysShop selectShopById(Long shopId);

    /**
     * 通过userId查询店铺
     *
     * @param userId 店铺ID
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
     * 删除店铺
     *
     * @param shopId 店铺id
     * @return 结果
     */
    int deleteShopById(Long shopId);

    /**
     * 修改店铺
     *
     * @param shopId 店铺id
     * @return 结果
     */
    int updateShopByAdmin(Long shopId);

    /**
     * 修改状态
     *
     * @param shop 店铺id
     * @return 结果
     */
    int updateShopStatus(SysShop shop);

}
