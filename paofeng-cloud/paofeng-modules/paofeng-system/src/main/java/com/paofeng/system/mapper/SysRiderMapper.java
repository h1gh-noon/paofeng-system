package com.paofeng.system.mapper;

import com.paofeng.system.api.domain.UserRelation;
import com.paofeng.system.domain.SysRider;

import java.util.List;

/**
 * 骑手表 数据层
 *
 * @author ruoyi
 */
public interface SysRiderMapper {
    /**
     * 根据条件分页查询骑手列表
     *
     * @param sysRider 骑手信息
     * @return 骑手信息集合信息
     */
    List<SysRider> selectRiderList(SysRider sysRider);

    /**
     * 通过骑手ID查询骑手
     *
     * @param riderId 骑手ID
     * @return 骑手对象信息
     */
    SysRider selectRiderById(Long riderId);

    /**
     * 通过userId查询骑手
     *
     * @param userId 骑手ID
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
     * 删除骑手
     *
     * @param riderId 骑手id
     * @return 结果
     */
    int deleteRiderById(Long riderId);

    /**
     * 修改骑手
     *
     * @param riderId 骑手id
     * @return 结果
     */
    int updateRiderByAdmin(Long riderId);

    /**
     * 修改状态
     *
     * @param rider 骑手id
     * @return 结果
     */
    int updateRiderStatus(SysRider rider);

    // 根据user_id批量查询联系人信息
    List<UserRelation> getRelationInfo(Long[] ids);
}
