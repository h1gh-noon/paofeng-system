package com.paofeng.system.service.impl;

import com.paofeng.common.core.enums.SysRoles;
import com.paofeng.common.security.service.TokenService;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.system.api.model.LoginUser;
import com.paofeng.system.domain.SysRider;
import com.paofeng.system.mapper.SysRiderMapper;
import com.paofeng.system.service.ISysRiderService;
import com.paofeng.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public int insertRider(SysRider rider) {
        // 如果没有rider角色就添加一个
        if (SecurityUtils.getLoginUser().getRoles().stream().noneMatch(e -> e.equals(SysRoles.SHOP.getName()))) {
            roleService.insertAuthUsers(SysRoles.SHOP.getId(), new Long[]{rider.getUserId()});
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getRoles().add(SysRoles.SHOP.getName());
            // 刷新redis
            tokenService.setLoginUser(loginUser);
        }

        return riderMapper.insertRider(rider);
    }

    @Override
    public int updateRider(SysRider rider) {
        return riderMapper.updateRider(rider);
    }

    @Override
    public int updateRiderStatus(SysRider rider) {
        return riderMapper.updateRiderStatus(rider);
    }

}
