package com.stone.elm.springboot.demo.business.chat.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationMessageRelatedAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationMessageRelatedVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationMessageRelatedService;
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

@Api(tags = "聊天会话/聊天会话关联消息表维护")
@RestController("ChatConversationMessageRelatedController")
@RequestMapping("/chat/chatConversationMessageRelated")
public class ChatConversationMessageRelatedController {

    @Autowired
    private IChatConversationMessageRelatedService iChatConversationMessageRelatedService;

    @PostMapping("/selectChatConversationMessageRelatedList")
    @ApiOperation(value = "查询聊天会话关联消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationMessageRelatedVO>> selectChatConversationMessageRelatedList (
            @ApiParam(name = "ChatConversationMessageRelatedAO", value = "聊天会话关联消息表实体AO") @RequestBody @Validated(ChatConversationMessageRelatedAO.selectGroup.class) ChatConversationMessageRelatedAO chatConversationMessageRelatedAO) {
        return iChatConversationMessageRelatedService.selectChatConversationMessageRelatedList(chatConversationMessageRelatedAO);
    }

    @PostMapping("/createChatConversationMessageRelatedList")
    @ApiOperation(value = "新增聊天会话关联消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationMessageRelatedVO>> createChatConversationMessageRelatedList (
            @ApiParam(name = "ChatConversationMessageRelatedAO", value = "聊天会话关联消息表实体AO") @RequestBody @Validated(ChatConversationMessageRelatedAO.createGroup.class) ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList) {
        return iChatConversationMessageRelatedService.createChatConversationMessageRelatedList(chatConversationMessageRelatedList);
    }

    @PostMapping("/markReadMessageForCurrentLogin")
    @ApiOperation(value = "当前会话消息已读 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationMessageRelatedVO>> markReadMessageForCurrentLogin (
            @ApiParam(name = "ChatConversationMessageRelatedAO", value = "聊天会话关联消息表实体AO") @RequestBody @Validated(ChatConversationMessageRelatedAO.markReadGroup.class) ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList) {
        return iChatConversationMessageRelatedService.markReadMessageForCurrentLogin(chatConversationMessageRelatedList);
    }

    @PostMapping("/updateChatConversationMessageRelatedList")
    @ApiOperation(value = "更新聊天会话关联消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationMessageRelatedVO>> updateChatConversationMessageRelatedList (
            @ApiParam(name = "ChatConversationMessageRelatedAO", value = "聊天会话关联消息表实体AO") @RequestBody @Validated(ChatConversationMessageRelatedAO.updateGroup.class) ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList) {
        return iChatConversationMessageRelatedService.updateChatConversationMessageRelatedList(chatConversationMessageRelatedList);
    }

    @PostMapping("/deleteChatConversationMessageRelatedList")
    @ApiOperation(value = "删除聊天会话关联消息表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationMessageRelatedVO>> deleteChatConversationMessageRelatedList (
            @ApiParam(name = "ChatConversationMessageRelatedAO", value = "聊天会话关联消息表实体AO") @RequestBody @Validated(ChatConversationMessageRelatedAO.deleteGroup.class) ValidList<ChatConversationMessageRelatedAO> chatConversationMessageRelatedList) {
        return iChatConversationMessageRelatedService.deleteChatConversationMessageRelatedList(chatConversationMessageRelatedList);
    }

}