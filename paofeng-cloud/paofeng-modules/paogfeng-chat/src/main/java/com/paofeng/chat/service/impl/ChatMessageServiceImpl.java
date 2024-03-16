package com.paofeng.chat.service.impl;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.ChatMessageVo;
import com.paofeng.chat.mapper.ChatMessageMapper;
import com.paofeng.chat.service.IChatMessageService;
import com.paofeng.common.core.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天Service业务层处理
 *
 * @author paofeng
 * @date 2024-02-22
 */
@Service
public class ChatMessageServiceImpl implements IChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    /**
     * 查询聊天
     *
     * @param id 聊天主键
     * @return 聊天
     */
    @Override
    public ChatMessageVo selectChatMessageById(String id) {
        return chatMessageMapper.selectChatMessageById(id);
    }

    /**
     * 查询聊天列表
     *
     * @param chatMessage 聊天
     * @return 聊天
     */
    @Override
    public List<ChatMessageVo> selectChatMessageList(ChatMessageVo chatMessage) {
        return chatMessageMapper.selectChatMessageList(chatMessage);
    }

    /**
     * 新增聊天
     *
     * @param chatMessage 聊天
     * @return 结果
     */
    @Override
    public int insertChatMessage(ChatMessage chatMessage) {
        chatMessage.setCreateTime(DateUtils.getNowDate());
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    /**
     * 修改聊天
     *
     * @param chatMessage 聊天
     * @return 结果
     */
    @Override
    public int updateChatMessage(ChatMessage chatMessage) {
        return chatMessageMapper.updateChatMessage(chatMessage);
    }

    /**
     * 批量删除聊天
     *
     * @param ids 需要删除的聊天主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByIds(String[] ids) {
        return chatMessageMapper.deleteChatMessageByIds(ids);
    }

    /**
     * 删除聊天信息
     *
     * @param id 聊天主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageById(String id) {
        return chatMessageMapper.deleteChatMessageById(id);
    }

    /**
     * 用户同步数据
     *
     * @param chatMessage senderId
     */
    @Override
    public List<ChatMessage> selectChatMessageListByUser(ChatMessageVo chatMessage) {
        // 最多支持查询 七天之内的记录
        Date nowDate = DateUtils.addDays(DateUtils.getNowDate(), -7);
        Date selectDate = chatMessage.getCreateTime();
        if (selectDate == null || nowDate.after(selectDate)) {
            selectDate = nowDate;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("beginCreateTime", new Timestamp(selectDate.getTime()));
        map.put("endCreateTime", new Timestamp(new Date().getTime()));
        chatMessage.setParams(map);

        return chatMessageMapper.selectChatMessageListByUser(chatMessage);
    }
}
