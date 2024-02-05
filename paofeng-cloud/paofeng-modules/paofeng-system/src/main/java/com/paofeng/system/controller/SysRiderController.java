package com.paofeng.system.controller;

import com.paofeng.common.core.web.controller.BaseController;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.core.web.page.TableDataInfo;
import com.paofeng.common.log.annotation.Log;
import com.paofeng.common.log.enums.BusinessType;
import com.paofeng.common.security.annotation.RequiresPermissions;
import com.paofeng.common.security.auth.AuthUtil;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.system.domain.SysRider;
import com.paofeng.system.service.ISysRiderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 骑手
 */
@RestController
@RequestMapping("/rider")
public class SysRiderController extends BaseController {

    @Resource
    private ISysRiderService riderService;

    /**
     * 获取列表
     */
    @RequiresPermissions("system:rider:list")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody(required = false) SysRider rider) {
        startPage();
        List<SysRider> list = riderService.selectRiderList(rider);
        return getDataTable(list);
    }

    // 根据id查询骑手
    @PostMapping("/selectRiderById")
    public AjaxResult selectRiderById(@Validated Long id) {

        SysRider rider = riderService.selectRiderById(id);

        // 如果userId不是当然登录人的 就检查角色权限是否为客服
        if (!Objects.equals(rider.getUserId(), SecurityUtils.getUserId())) {
            AuthUtil.checkRole("customer_service*");
        }
        return success(rider);
    }

    // 查询当前登陆人的骑手
    @GetMapping("/selectRiderByUserId")
    public AjaxResult selectRiderByUserId() {
        SysRider sysRider = riderService.selectRiderByUserId(SecurityUtils.getUserId());
        // 检测是否可用
        if ("1".equals(sysRider.getStatus())) {
            return success(sysRider);
        } else {
            return error(sysRider.getStatus());
        }
    }

    @Log(title = "骑手管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysRider rider) {

        rider.setUserId(SecurityUtils.getUserId());

        if (riderService.insertRider(rider) > 0) {
            return success(rider.getRiderId());
        } else {
            return error();
        }
    }

    @Log(title = "骑手管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@Validated @RequestBody SysRider rider) {
        return toAjax(riderService.updateRider(rider));
    }

    @RequiresPermissions("system:rider:edit")
    @Log(title = "骑手管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateByAdmin")
    public AjaxResult updateByAdmin(@Validated @RequestBody SysRider rider) {
        return toAjax(riderService.updateRiderByAdmin(rider));
    }

    // 修改状态
    @RequiresPermissions("system:rider:edit")
    @Log(title = "骑手管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@Validated @RequestBody SysRider rider) {
        return toAjax(riderService.updateRiderStatus(rider));
    }

}
