package com.paofeng.system.api;

import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.constant.ServiceNameConstants;
import com.paofeng.common.core.domain.R;
import com.paofeng.system.api.domain.SysUser;
import com.paofeng.system.api.domain.UserRelation;
import com.paofeng.system.api.factory.RemoteUserFallbackFactory;
import com.paofeng.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory =
        RemoteUserFallbackFactory.class)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    R<LoginUser> getUserInfo(@PathVariable("username") String username,
                             @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/infoByPhone/{phone}")
    R<LoginUser> getUserInfoByPhone(@PathVariable("phone") String username,
                             @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    R<Boolean> registerUserInfo(@RequestBody SysUser sysUser,
                                @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取联系人信息
     *
     * @param ids 用户id
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/getRelationInfo")
    R<List<UserRelation>> getRelationInfo(@RequestBody Long [] ids,
                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
