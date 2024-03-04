package com.paofeng.system.service;


import com.paofeng.system.domain.SysRider;

import java.util.List;

/**
 * 骑手 业务层
 *
 * @author ruoyi
 */
public interface ISysRiderService {
    /**
     * 根据条件分页查询骑手列表
     *
     * @param rider 骑手信息
     * @return 骑手信息集合信息
     */
    List<SysRider> selectRiderList(SysRider rider);

    /**
     * 通过骑手ID查询骑手
     *
     * @param riderId 骑手ID
     * @return 骑手对象信息
     */
    SysRider selectRiderById(Long riderId);

    /**
     * 通过用户ID查询骑手
     *
     * @param userId ID
     * @return 骑手对象信息
     */
    SysRider selectRiderByUserId(Long userId);

    /**
     * 新增骑手信息
     *
     * @param rider 骑手信息
     * @return 结果
     */
    int insertRider(SysRider rider);

    /**
     * 修改骑手信息
     *
     * @param rider 骑手信息
     * @return 结果
     */
    int updateRider(SysRider rider);


    /**
     * 修改骑手状态
     *
     * @param rider 骑手信息
     * @return 结果
     */
    int updateRiderStatus(SysRider rider);

}
