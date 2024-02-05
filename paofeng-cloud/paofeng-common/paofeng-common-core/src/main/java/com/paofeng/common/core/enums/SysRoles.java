package com.paofeng.common.core.enums;

public enum SysRoles {
    // 商家
    SHOP(100L, "shop"),
    // 骑手
    RIDER(102L, "rider");

    private final Long id;
    private final String name;

    SysRoles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
