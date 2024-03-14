package com.paofeng.chat.mapper;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.ChatMessageVo;
import com.paofeng.chat.domain.SendMessage;

import java.util.List;

/**
 * 聊天Mapper接口
 *
 * @author paofeng
 * @date 2024-02-22
 */
public interface ChatMessageMapper {
    /**
     * 查询聊天
     *
     * @param id 聊天主键
     * @return 聊天
     */
    public ChatMessageVo selectChatMessageById(String id);

    /**
     * 查询聊天列表
     *
     * @param chatMessage 聊天
     * @return 聊天集合
     */
    public List<ChatMessageVo> selectChatMessageList(ChatMessageVo chatMessage);

    /**
     * 新增聊天
     *
     * @param chatMessage 聊天
     * @return 结果
     */
    public int insertChatMessage(ChatMessage chatMessage);

    /**
     * 修改聊天
     *
     * @param chatMessage 聊天
     * @return 结果
     */
    public int updateChatMessage(ChatMessage chatMessage);

    /**
     * 删除聊天
     *
     * @param id 聊天主键
     * @return 结果
     */
    public int deleteChatMessageById(String id);

    /**
     * 批量删除聊天
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChatMessageByIds(String[] ids);

    /**
     * 用户同步数据
     *
     * @param chatMessage senderId
     */
    public List<SendMessage> selectChatMessageListByUser(ChatMessage chatMessage);
}
