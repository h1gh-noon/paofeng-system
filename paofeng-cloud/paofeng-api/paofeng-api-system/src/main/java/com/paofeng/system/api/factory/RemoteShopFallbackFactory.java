package com.paofeng.system.api.factory;

import com.paofeng.common.core.domain.R;
import com.paofeng.system.api.RemoteShopService;
import com.paofeng.system.api.domain.SysShop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteShopFallbackFactory implements FallbackFactory<RemoteShopService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteShopFallbackFactory.class);

    @Override
    public RemoteShopService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteShopService() {
            @Override
            public R<SysShop> getShopInfoByUserId(Long userId, String source) {
                return R.fail("获取店铺失败:" + throwable.getMessage());
            }

        };
    }
}
