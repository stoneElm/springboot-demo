package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao.PersonalDocAO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo.PersonalDocVO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.IPersonalDocService;
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

@Api(tags = "个人中心/个人文档表维护")
@RestController("personalDocCenterController")
@RequestMapping("/personalCenter/personalDoc")
public class PersonalDocController {
    @Autowired
    private IPersonalDocService iPersonalDocService;

    @PostMapping("/selectPersonalDocList")
    @ApiOperation(value = "查询个人文档表列表 维护人:Lan StoneElm")
    public ResponseResult<List<PersonalDocVO>> selectPersonalDocList (
            @ApiParam(name = "PersonalDocAO", value = "个人文档表实体AO") @RequestBody @Validated(PersonalDocAO.selectGroup.class) PersonalDocAO personalDocAO) {
        return iPersonalDocService.selectPersonalDocList(personalDocAO);
    }

    @PostMapping("/createPersonalDocList")
    @ApiOperation(value = "新增个人文档表列表 维护人:Lan StoneElm")
    public ResponseResult<List<PersonalDocVO>> createPersonalDocList (
            @ApiParam(name = "PersonalDocAO", value = "个人文档表实体AO") @RequestBody @Validated(PersonalDocAO.createGroup.class) ValidList<PersonalDocAO> personalDocList) {
        return iPersonalDocService.createPersonalDocList(personalDocList);
    }

    @PostMapping("/updatePersonalDocList")
    @ApiOperation(value = "更新个人文档表列表 维护人:Lan StoneElm")
    public ResponseResult<List<PersonalDocVO>> updatePersonalDocList (
            @ApiParam(name = "PersonalDocAO", value = "个人文档表实体AO") @RequestBody @Validated(PersonalDocAO.updateGroup.class) ValidList<PersonalDocAO> personalDocList) {
        return iPersonalDocService.updatePersonalDocList(personalDocList);
    }

    @PostMapping("/deletePersonalDocList")
    @ApiOperation(value = "删除个人文档表列表 维护人:Lan StoneElm")
    public ResponseResult<List<PersonalDocVO>> deletePersonalDocList (
            @ApiParam(name = "PersonalDocAO", value = "个人文档表实体AO") @RequestBody @Validated(PersonalDocAO.deleteGroup.class) ValidList<PersonalDocAO> personalDocList) {
        return iPersonalDocService.deletePersonalDocList(personalDocList);
    }

    @PostMapping("/selectPersonalDocPath")
    @ApiOperation(value = "查询当前文档路径 维护人:Lan StoneElm")
    public ResponseResult<List<PersonalDocVO>> selectPersonalDocPath (
            @ApiParam(name = "PersonalDocAO", value = "个人文档表实体AO") @RequestBody PersonalDocAO personalDocAO) {
        return iPersonalDocService.selectPersonalDocPath(personalDocAO);
    }

}
