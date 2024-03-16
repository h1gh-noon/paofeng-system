package com.paofeng.order.controller;

import com.paofeng.common.core.constant.HttpStatus;
import com.paofeng.common.core.domain.Validation;
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
import com.paofeng.common.core.domain.Order;
import com.paofeng.order.domain.OrderVo;
import com.paofeng.order.service.IOrderService;
import com.paofeng.order.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private RabbitMQService rabbitMQService;


    /**
     * 查询订单列表
     */
    @RequiresPermissions("order:order:list")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody(required = false) OrderVo order) {
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 商家查询订单列表
     */
    @PostMapping("/shopList")
    public TableDataInfo shopList(@RequestBody(required = false) OrderVo order) {
        if (order == null) {
            order = new OrderVo();
        }
        order.setCreatId(SecurityUtils.getUserId());
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 骑手查询订单列表
     */
    @PostMapping("/riderList")
    public TableDataInfo riderList(@RequestBody(required = false) OrderVo order) {
        if (order == null) {
            order = new OrderVo();
        }
        order.setCurrentRider(SecurityUtils.getUserId());
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 骑手 订单列表
     */
    @RequiresPermissions("order:order:take")
    @PostMapping("/riderTakeOrderList")
    public TableDataInfo riderTakeOrderList() {
        OrderVo order = new OrderVo();
        order.setStatus(Order.TYPE_PUBLISH);
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
    public void export(HttpServletResponse response, @RequestBody(required = false) OrderVo order) {
        List<OrderVo> list = orderService.selectOrderList(order);
        ExcelUtil<OrderVo> util = new ExcelUtil<OrderVo>(OrderVo.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @RequiresPermissions("order:order:query")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") @RequestParam String orderId) {
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
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("order:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody @Validated(Validation.Update.class) Order order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 修改订单
     */
    @Log(title = "商家修改配送费", businessType = BusinessType.UPDATE)
    @PostMapping("/updateDeliveryFee")
    public AjaxResult updateDeliveryFee(@RequestBody @Validated(Validation.Update.class) Order order) {
        OrderVo orderVo = orderService.selectOrderByOrderId(order.getOrderId());
        Long currentUserId = SecurityUtils.getUserId();
        if (!AuthUtil.hasPermi("order:order:query")
                && !currentUserId.equals(orderVo.getCreatId())) {
            // 权限校验 客服 店铺
            throw new NotPermissionException(HttpStatus.UNAUTHORIZED + "");
        }
        Order o = new Order();
        o.setOrderId(order.getOrderId());
        o.setDeliveryFee(order.getDeliveryFee());

        return toAjax(orderService.updateOrder(o));
    }

    /**
     * 修改订单
     */
    @Log(title = "发布订单", businessType = BusinessType.UPDATE)
    @RequestMapping("/subOrder")
    public AjaxResult subOrder(@RequestParam String orderId) {
        OrderVo orderVo = orderService.selectOrderByOrderId(orderId);
        Long currentUserId = SecurityUtils.getUserId();
        if (!AuthUtil.hasPermi("order:order:query")
                && !currentUserId.equals(orderVo.getCreatId())) {
            // 权限校验 客服 店铺
            throw new NotPermissionException(HttpStatus.UNAUTHORIZED + "");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(Order.TYPE_PUBLISH);
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("order:order:take")
    @Log(title = "骑手接单", businessType = BusinessType.UPDATE)
    @RequestMapping("/takeOrder")
    public AjaxResult takeOrder(@RequestParam String orderId) {
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
    @Log(title = "骑手取到货物", businessType = BusinessType.UPDATE)
    @RequestMapping("/takeOrderGoods")
    public AjaxResult takeOrderGoods(@RequestParam String orderId) {
        if (orderService.takeOrderGoods(orderId) > 0) {
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
    @RequestMapping("/successOrder")
    public AjaxResult successOrder(@RequestParam String orderId) {
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
    @RequestMapping("/cancelOrder")
    public AjaxResult cancelOrder(@RequestParam String orderId) {
        if (orderService.cancelOrder(orderId) > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @RequestMapping("/test")
    public AjaxResult test() {
        rabbitMQService.sendMessage("null", "ss");
        return AjaxResult.success();
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
