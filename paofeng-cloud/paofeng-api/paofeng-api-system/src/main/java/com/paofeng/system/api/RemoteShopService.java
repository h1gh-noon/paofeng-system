package com.paofeng.system.api;

import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.constant.ServiceNameConstants;
import com.paofeng.common.core.domain.R;
import com.paofeng.system.api.domain.SysShop;
import com.paofeng.system.api.factory.RemoteShopFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 店铺服务
 *
 */
@FeignClient(contextId = "remoteShopService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory =
        RemoteShopFallbackFactory.class)
public interface RemoteShopService {
    /**
     * 通过用户名查询店铺信息
     *
     * @param userId 用户名
     * @return 结果
     */
    @GetMapping("/shop/info/{userId}")
    R<SysShop> getShopInfoByUserId(@PathVariable("userId") Long userId,
                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
