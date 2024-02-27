package com.stone.elm.springboot.demo.business.system.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.types.ValidList;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsValAO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsVO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsValVO;
import com.stone.elm.springboot.demo.business.system.service.ICodeClsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "系统维护/标准代码维护")
@RestController("systemCodeClsController")
@RequestMapping("/system/codeCls")
public class CodeClsController {
    @Autowired
    private ICodeClsService iCodeClsService;

    @PostMapping("/queryCodeClsList")
    @ApiOperation(value = "查询标准代码分类列表 维护人:Lan StoneElm")
    public ResponseResult<List<CodeClsVO>> queryCodeClsList (
            @ApiParam(name = "CodeClsAO", value = "标准代码分类实体AO") @RequestBody CodeClsAO codeClsAO) {
        return iCodeClsService.queryCodeClsList(codeClsAO);
    }

    @PostMapping("/saveCodeClsList")
    @ApiOperation(value = "保存标准代码分类列表 维护人:Lan StoneElm")
    public ResponseResult<List<CodeClsVO>> saveCodeClsList (
            @ApiParam(name = "CodeClsAO", value = "标准代码分类实体AO") @RequestBody List<CodeClsAO> codeClsAOList) {
        return iCodeClsService.saveCodeClsList(codeClsAOList);
    }

    @PostMapping("/createCodeClsList")
    @ApiOperation(value = "查询标准代码分类列表 维护人:Lan StoneElm")
    public ResponseResult<List<CodeClsVO>> createCodeClsList (
            @ApiParam(name = "CodeClsAO", value = "标准代码分类实体AO") @RequestBody @Validated(CodeClsAO.createGroup.class) ValidList<CodeClsAO> codeClsList) {
        return iCodeClsService.createCodeClsList(codeClsList);
    }

    @PostMapping("/updateCodeClsList")
    public ResponseResult<List<CodeClsVO>> updateCodeClsList (
            @ApiParam(name = "CodeClsAO", value = "标准代码分类实体AO") @RequestBody @Validated(CodeClsAO.updateGroup.class) ValidList<CodeClsAO> codeClsAOList) {
        return iCodeClsService.updateCodeClsList(codeClsAOList);
    }

    @PostMapping("/deleteCodeClsList")
    public ResponseResult<List<CodeClsVO>> deleteCodeClsList (
            @ApiParam(name = "CodeClsAO", value = "标准代码分类实体AO") @RequestBody @Validated(CodeClsAO.updateGroup.class) ValidList<CodeClsAO> codeClsList) {
        return iCodeClsService.deleteCodeClsList(codeClsList);
    }

    @PostMapping("/queryCodeClsValList")
    public ResponseResult<List<CodeClsValVO>> queryCodeClsValList (
            @ApiParam(name = "CodeClsValAO", value = "标准代码值实体AO") @RequestBody CodeClsValAO codeClsValAO) {
        return iCodeClsService.queryCodeClsValList(codeClsValAO);
    }

    @PostMapping("/saveCodeClsValList")
    public ResponseResult<List<CodeClsValVO>> saveCodeClsValList (
            @ApiParam(name = "CodeClsValAO", value = "标准代码值实体AO") @RequestBody ValidList<CodeClsValAO> codeClsValList) {
        return iCodeClsService.saveCodeClsValList(codeClsValList);
    }

    @PostMapping("/createCodeClsValList")
    public ResponseResult<List<CodeClsValVO>> createCodeClsValList (
            @ApiParam(name = "CodeClsValAO", value = "标准代码值实体AO") @RequestBody @Validated(CodeClsValAO.createGroup.class) ValidList<CodeClsValAO> codeClsValList) {
        return iCodeClsService.createCodeClsValList(codeClsValList);
    }

    @PostMapping("/updateCodeClsValList")
    public ResponseResult<List<CodeClsValVO>> updateCodeClsValList (
            @ApiParam(name = "CodeClsValAO", value = "标准代码值实体AO") @RequestBody @Validated(CodeClsValAO.updateGroup.class) ValidList<CodeClsValAO> codeClsValList) {
        return iCodeClsService.updateCodeClsValList(codeClsValList);
    }

    @PostMapping("/deleteCodeClsValList")
    public ResponseResult<List<CodeClsValVO>> deleteCodeClsValList (
            @ApiParam(name = "CodeClsValAO", value = "标准代码值实体AO") @RequestBody @Validated(CodeClsValAO.deleteGroup.class) ValidList<CodeClsValAO> codeClsValList) {
        return iCodeClsService.deleteCodeClsValList(codeClsValList);
    }
}
