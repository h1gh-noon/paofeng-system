package com.paofeng.system.service.impl;

import com.paofeng.common.core.enums.SysRoles;
import com.paofeng.common.security.service.TokenService;
import com.paofeng.system.api.domain.SysShop;
import com.paofeng.system.domain.SysRider;
import com.paofeng.system.mapper.SysRiderMapper;
import com.paofeng.system.service.ISysPermissionService;
import com.paofeng.system.service.ISysRiderService;
import com.paofeng.system.service.ISysRoleService;
import com.paofeng.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRiderServiceImpl implements ISysRiderService {

    @Resource
    private SysRiderMapper riderMapper;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private TokenService tokenService;

    @Resource
    private ISysPermissionService permissionService;

    @Resource
    private ISysUserService userService;


    @Override
    public List<SysRider> selectRiderList(SysRider rider) {
        return riderMapper.selectRiderList(rider);
    }

    @Override
    public SysRider selectRiderById(Long riderId) {
        return riderMapper.selectRiderById(riderId);
    }

    @Override
    public SysRider selectRiderByUserId(Long userId) {
        return riderMapper.selectRiderByUserId(userId);
    }

    @Override
    public int insertRider(SysRider rider) {
        return riderMapper.insertRider(rider);
    }

    @Override
    public int updateRider(SysRider rider) {
        return riderMapper.updateRider(rider);
    }

    @Override
    @Transactional
    public int updateRiderStatus(SysRider rider) {
        SysRider sysRider = selectRiderById(rider.getRiderId());

        if (SysShop.STATUS_ENABLE.equals(rider.getStatus())) { // 启用
            roleService.insertAuthUsers(SysRoles.RIDER.getId(), new Long[]{sysRider.getUserId()});
        } else {
            roleService.deleteAuthUsers(SysRoles.RIDER.getId(), new Long[]{sysRider.getUserId()});
        }

        return riderMapper.updateRiderStatus(rider);
    }

}
