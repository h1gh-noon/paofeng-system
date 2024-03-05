package com.paofeng.order.controller;

import com.paofeng.common.core.constant.HttpStatus;
import com.paofeng.common.core.exception.auth.NotPermissionException;
import com.paofeng.common.core.utils.poi.ExcelUtil;
import com.paofeng.common.core.web.controller.BaseController;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.core.web.page.TableDataInfo;
import com.paofeng.common.log.annotation.Log;
import com.paofeng.common.log.enums.BusinessType;
import com.paofeng.common.security.annotation.RequiresPermissions;
import com.paofeng.common.security.auth.AuthUtil;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.order.domain.Order;
import com.paofeng.order.domain.OrderVo;
import com.paofeng.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 *
 * @author paofeng
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    /**
     * 查询订单列表
     */
    @RequiresPermissions("order:order:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderVo order) {
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 商家查询订单列表
     */
    @GetMapping("/shopList")
    public TableDataInfo shopList(OrderVo order) {
        order.setCreatId(SecurityUtils.getUserId());
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 骑手查询订单列表
     */
    @GetMapping("/riderList")
    public TableDataInfo riderList(OrderVo order) {
        order.setCurrentRider(SecurityUtils.getUserId());
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("order:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderVo order) {
        List<OrderVo> list = orderService.selectOrderList(order);
        ExcelUtil<OrderVo> util = new ExcelUtil<OrderVo>(OrderVo.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @RequiresPermissions("order:order:query")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId) {
        OrderVo orderVo = orderService.selectOrderByOrderId(orderId);
        Long currentUserId = SecurityUtils.getUserId();
        if (!AuthUtil.hasPermi("order:order:query")
                || !currentUserId.equals(orderVo.getCreatId())
                || !currentUserId.equals(orderVo.getCurrentRider())) {
            // 权限校验 客服店铺骑手可见
            throw new NotPermissionException(HttpStatus.UNAUTHORIZED + "");
        }
        return success(orderVo);
    }

    /**
     * 新增订单
     */
    @RequiresPermissions("order:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("order:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult edit(@RequestBody Order order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 修改订单
     */
    @Log(title = "发布订单", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult subOrder(String orderId) {
        OrderVo orderVo = orderService.selectOrderByOrderId(orderId);
        Long currentUserId = SecurityUtils.getUserId();
        if (!AuthUtil.hasPermi("order:order:query")
                || !currentUserId.equals(orderVo.getCreatId())) {
            // 权限校验 客服 店铺
            throw new NotPermissionException(HttpStatus.UNAUTHORIZED + "");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setType(Order.TYPE_PUBLISH);
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("order:order:take")
    @Log(title = "骑手接单订单", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult takeOrder(String orderId) {
        if (orderService.takeOrder(orderId) > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("order:order:take")
    @Log(title = "骑手送达订单", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult successOrder(String orderId) {
        if (orderService.successOrder(orderId) > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改订单
     */
    @Log(title = "取消订单", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult cancelOrder(String orderId) {
        if (orderService.cancelOrder(orderId) > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    // /**
    //  * 删除订单
    //  */
    // @RequiresPermissions("order:order:remove")
    // @Log(title = "订单", businessType = BusinessType.DELETE)
    // @DeleteMapping("/{orderIds}")
    // public AjaxResult remove(@PathVariable String[] orderIds) {
    //     return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    // }
}
