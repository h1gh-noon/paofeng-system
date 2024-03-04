package com.paofeng.chat.controller;

import com.paofeng.chat.domain.ChatMessageVo;
import com.paofeng.chat.service.IChatMessageService;
import com.paofeng.chat.service.RabbitMQService;
import com.paofeng.common.core.utils.poi.ExcelUtil;
import com.paofeng.common.core.web.controller.BaseController;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.core.web.page.TableDataInfo;
import com.paofeng.common.log.annotation.Log;
import com.paofeng.common.log.enums.BusinessType;
import com.paofeng.common.security.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 聊天Controller
 *
 * @author paofeng
 * @date 2024-02-22
 */
@RestController
@RequestMapping("/chat")
public class ChatMessageController extends BaseController {

    @Resource
    private IChatMessageService chatMessageService;

    @Resource
    private RabbitMQService rabbitMQService;

    /**
     * 查询聊天列表
     */
    @RequiresPermissions("chat:chat:list")
    @GetMapping("/list")
    public TableDataInfo list(ChatMessageVo chatMessage) {
        startPage();
        List<ChatMessageVo> list = chatMessageService.selectChatMessageList(chatMessage);
        return getDataTable(list);
    }

    /**
     * 导出聊天列表
     */
    @RequiresPermissions("chat:chat:export")
    @Log(title = "聊天", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChatMessageVo chatMessage) {
        List<ChatMessageVo> list = chatMessageService.selectChatMessageList(chatMessage);
        ExcelUtil<ChatMessageVo> util = new ExcelUtil<ChatMessageVo>(ChatMessageVo.class);
        util.exportExcel(response, list, "聊天数据");
    }

    /**
     * 获取聊天详细信息
     */
    @RequiresPermissions("chat:chat:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(chatMessageService.selectChatMessageById(id));
    }

    @RequestMapping("/test")
    public AjaxResult test(String msg, String r) {
        System.out.println(msg);
        rabbitMQService.sendMessage(r, msg);
        return success();
    }

    /**
     * 新增聊天
     */
    // @RequiresPermissions("chat:chat:add")
    // @Log(title = "聊天", businessType = BusinessType.INSERT)
    // @PostMapping
    // public AjaxResult add(@RequestBody ChatMessage chatMessage) {
    //     return toAjax(chatMessageService.insertChatMessage(chatMessage));
    // }

    /**
     * 修改聊天
     */
    // @RequiresPermissions("chat:chat:edit")
    // @Log(title = "聊天", businessType = BusinessType.UPDATE)
    // @PutMapping
    // public AjaxResult edit(@RequestBody ChatMessage chatMessage) {
    //     return toAjax(chatMessageService.updateChatMessage(chatMessage));
    // }

    /**
     * 删除聊天
     */
    // @RequiresPermissions("chat:chat:remove")
    // @Log(title = "聊天", businessType = BusinessType.DELETE)
    // @DeleteMapping("/{ids}")
    // public AjaxResult remove(@PathVariable String[] ids) {
    //     return toAjax(chatMessageService.deleteChatMessageByIds(ids));
    // }
}
