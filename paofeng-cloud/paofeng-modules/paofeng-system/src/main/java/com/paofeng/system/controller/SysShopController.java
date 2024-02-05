package com.paofeng.system.controller;

import com.paofeng.common.core.web.controller.BaseController;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.core.web.page.TableDataInfo;
import com.paofeng.common.log.annotation.Log;
import com.paofeng.common.log.enums.BusinessType;
import com.paofeng.common.security.annotation.RequiresPermissions;
import com.paofeng.common.security.auth.AuthUtil;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.system.domain.SysShop;
import com.paofeng.system.service.ISysShopService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 店铺
 */
@RestController
@RequestMapping("/shop")
public class SysShopController extends BaseController {

    @Resource
    private ISysShopService shopService;

    /**
     * 获取列表
     */
    @RequiresPermissions("system:shop:list")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody(required = false) SysShop shop) {
        startPage();
        List<SysShop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    // 根据id查询店铺
    @PostMapping("/selectShopById")
    public AjaxResult selectShopById(@Validated Long id) {

        SysShop shop = shopService.selectShopById(id);

        // 如果userId不是当然登录人的 就检查角色权限是否为客服
        if (!Objects.equals(shop.getUserId(), SecurityUtils.getUserId())) {
            AuthUtil.checkRole("customer_service*");
        }
        return success(shop);
    }

    // 查询当前登陆人的店铺
    @GetMapping("/selectShopByUserId")
    public AjaxResult selectShopByUserId() {
        SysShop sysShop = shopService.selectShopByUserId(SecurityUtils.getUserId());
        // 检测是否可用
        if ("1".equals(sysShop.getStatus())) {
            return success(sysShop);
        } else {
            return error(sysShop.getStatus());
        }
    }

    @Log(title = "店铺管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysShop shop) {

        shop.setUserId(SecurityUtils.getUserId());

        if (shopService.insertShop(shop) > 0) {
            return success(shop.getShopId());
        } else {
            return error();
        }
    }

    @Log(title = "店铺管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@Validated @RequestBody SysShop shop) {
        return toAjax(shopService.updateShop(shop));
    }

    @RequiresPermissions("system:shop:edit")
    @Log(title = "店铺管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateByAdmin")
    public AjaxResult updateByAdmin(@Validated @RequestBody SysShop shop) {
        return toAjax(shopService.updateShopByAdmin(shop));
    }

    // 修改状态
    @RequiresPermissions("system:shop:edit")
    @Log(title = "店铺管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@Validated @RequestBody SysShop shop) {
        return toAjax(shopService.updateShopStatus(shop));
    }

}
