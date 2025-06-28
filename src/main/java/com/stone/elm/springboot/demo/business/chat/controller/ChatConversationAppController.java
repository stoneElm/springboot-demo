package com.stone.elm.springboot.demo.business.chat.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.chat.model.ao.ChatConversationAppAO;
import com.stone.elm.springboot.demo.business.chat.model.vo.ChatConversationAppVO;
import com.stone.elm.springboot.demo.business.chat.service.IChatConversationAppService;
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

@Api(tags = "聊天会话/聊天会话申请表维护")
@RestController("ChatConversationAppController")
@RequestMapping("/chat/chatConversationApp")
public class ChatConversationAppController {

    @Autowired
    private IChatConversationAppService iChatConversationAppService;

    @PostMapping("/selectChatConversationAppList")
    @ApiOperation(value = "查询聊天会话申请表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationAppVO>> selectChatConversationAppList (
            @ApiParam(name = "ChatConversationAppAO", value = "聊天会话申请表实体AO") @RequestBody @Validated(ChatConversationAppAO.selectGroup.class) ChatConversationAppAO chatConversationAppAO) {
        return iChatConversationAppService.selectChatConversationAppList(chatConversationAppAO);
    }

    @PostMapping("/selectLoginUserInvitedInfo")
    @ApiOperation(value = "获取当前登陆人聊天会话受邀信息 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationAppVO>> selectLoginUserInvitedInfo (
            @ApiParam(name = "ChatConversationAppAO", value = "聊天会话申请表实体AO") @RequestBody @Validated(ChatConversationAppAO.selectGroup.class) ChatConversationAppAO chatConversationAppAO) {
        return iChatConversationAppService.selectLoginUserInvitedInfo(chatConversationAppAO);
    }

    @PostMapping("/createChatConversationAppList")
    @ApiOperation(value = "新增聊天会话申请表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationAppVO>> createChatConversationAppList (
            @ApiParam(name = "ChatConversationAppAO", value = "聊天会话申请表实体AO") @RequestBody @Validated(ChatConversationAppAO.createGroup.class) ValidList<ChatConversationAppAO> chatConversationAppList) {
        return iChatConversationAppService.createChatConversationAppList(chatConversationAppList);
    }

    @PostMapping("/updateChatConversationAppList")
    @ApiOperation(value = "更新聊天会话申请表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationAppVO>> updateChatConversationAppList (
            @ApiParam(name = "ChatConversationAppAO", value = "聊天会话申请表实体AO") @RequestBody @Validated(ChatConversationAppAO.updateGroup.class) ValidList<ChatConversationAppAO> chatConversationAppList) {
        return iChatConversationAppService.updateChatConversationAppList(chatConversationAppList);
    }

    @PostMapping("/deleteChatConversationAppList")
    @ApiOperation(value = "删除聊天会话申请表列表 维护人:Lan StoneElm")
    public ResponseResult<List<ChatConversationAppVO>> deleteChatConversationAppList (
            @ApiParam(name = "ChatConversationAppAO", value = "聊天会话申请表实体AO") @RequestBody @Validated(ChatConversationAppAO.deleteGroup.class) ValidList<ChatConversationAppAO> chatConversationAppList) {
        return iChatConversationAppService.deleteChatConversationAppList(chatConversationAppList);
    }

}
