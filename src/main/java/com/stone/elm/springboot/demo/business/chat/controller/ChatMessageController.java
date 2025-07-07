package com.stone.elm.springboot.demo.business.chat.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatMessageAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatMessageVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "聊天会话/聊天消息表维护")
@RestController("ChatMessageController")
@RequestMapping("/chat/chatMessage")
public class ChatMessageController {
    @Autowired
    private IChatMessageService iChatMessageService;

    @PostMapping("/selectChatMessageList")
    @ApiOperation(value = "查询聊天消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatMessageVO>> selectChatMessageList (
            @ApiParam(name = "ChatMessageAO", value = "聊天消息表实体AO") @RequestBody @Validated(ChatMessageAO.selectGroup.class) ChatMessageAO chatMessageAO) {
        return iChatMessageService.selectChatMessageList(chatMessageAO);
    }

    @PostMapping("/selectChatMessageListByConversationID")
    @ApiOperation(value = "查询聊天消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatMessageVO>> selectChatMessageListByConversationID (
            @ApiParam(name = "ChatMessageAO", value = "聊天消息表实体AO") @RequestBody @Validated(ChatMessageAO.selectBYCIDGroup.class) ChatMessageAO chatMessageAO) {
        return iChatMessageService.selectChatMessageListByConversationID(chatMessageAO);
    }

    @PostMapping("/createChatMessageList")
    @ApiOperation(value = "新增聊天消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatMessageVO>> createChatMessageList (
            @ApiParam(name = "ChatMessageAO", value = "聊天消息表实体AO") @RequestBody @Validated(ChatMessageAO.createGroup.class) ValidList<ChatMessageAO> chatMessageList) {
        return iChatMessageService.createChatMessageList(chatMessageList);
    }

    @PostMapping("/updateChatMessageList")
    @ApiOperation(value = "更新聊天消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatMessageVO>> updateChatMessageList (
            @ApiParam(name = "ChatMessageAO", value = "聊天消息表实体AO") @RequestBody @Validated(ChatMessageAO.updateGroup.class) ValidList<ChatMessageAO> chatMessageList) {
        return iChatMessageService.updateChatMessageList(chatMessageList);
    }

    @PostMapping("/deleteChatMessageList")
    @ApiOperation(value = "删除聊天消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatMessageVO>> deleteChatMessageList (
            @ApiParam(name = "ChatMessageAO", value = "聊天消息表实体AO") @RequestBody @Validated(ChatMessageAO.deleteGroup.class) ValidList<ChatMessageAO> chatMessageList) {
        return iChatMessageService.deleteChatMessageList(chatMessageList);
    }

}
