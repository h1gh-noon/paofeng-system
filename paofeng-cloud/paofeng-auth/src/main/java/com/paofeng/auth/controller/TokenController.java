package com.paofeng.auth.controller;

import com.paofeng.auth.form.LoginBody;
import com.paofeng.auth.form.RegisterBody;
import com.paofeng.auth.service.SysLoginService;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.core.utils.JwtUtils;
import com.paofeng.common.core.utils.StringUtils;
import com.paofeng.common.security.auth.AuthUtil;
import com.paofeng.common.security.service.TokenService;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 *
 * @author ruoyi
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @PostMapping("loginByPhone")
    public R<?> loginByPhone(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.loginByPhone(form.getPhone(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        if (registerBody.getUsername() == null) {
            sysLoginService.registerCustomer(registerBody.getPhone(), registerBody.getPassword());
        } else {
            sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        }
        return R.ok();
    }
}
