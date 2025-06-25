package com.stone.elm.springboot.demo.business.chat.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationService;
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

@Api(tags = "聊天会话/聊天会话表维护")
@RestController("ChatConversationController")
@RequestMapping("/chat/chatConversation")
public class ChatConversationController {
    @Autowired
    private IChatConversationService iChatConversationService;

    @PostMapping("/selectChatConversationList")
    @ApiOperation(value = "查询聊天会话表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationVO>> selectChatConversationList (
            @ApiParam(name = "ChatConversationAO", value = "聊天会话表实体AO") @RequestBody @Validated(ChatConversationAO.selectGroup.class) ChatConversationAO chatConversationAO) {
        return iChatConversationService.selectChatConversationList(chatConversationAO);
    }

    @PostMapping("/createChatConversationList")
    @ApiOperation(value = "新增聊天会话表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationVO>> createChatConversationList (
            @ApiParam(name = "ChatConversationAO", value = "聊天会话表实体AO") @RequestBody @Validated(ChatConversationAO.createGroup.class) ValidList<ChatConversationAO> chatConversationList) {
        return iChatConversationService.createChatConversationList(chatConversationList);
    }

    @PostMapping("/updateChatConversationList")
    @ApiOperation(value = "更新聊天会话表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationVO>> updateChatConversationList (
            @ApiParam(name = "ChatConversationAO", value = "聊天会话表实体AO") @RequestBody @Validated(ChatConversationAO.updateGroup.class) ValidList<ChatConversationAO> chatConversationList) {
        return iChatConversationService.updateChatConversationList(chatConversationList);
    }

    @PostMapping("/deleteChatConversationList")
    @ApiOperation(value = "删除聊天会话表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationVO>> deleteChatConversationList (
            @ApiParam(name = "ChatConversationAO", value = "聊天会话表实体AO") @RequestBody @Validated(ChatConversationAO.deleteGroup.class) ValidList<ChatConversationAO> chatConversationList) {
        return iChatConversationService.deleteChatConversationList(chatConversationList);
    }

}