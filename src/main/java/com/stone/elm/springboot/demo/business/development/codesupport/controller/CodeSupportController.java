package com.stone.elm.springboot.demo.business.development.codesupport.controller;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.TableQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.TableQueryVO;
import com.stone.elm.springboot.demo.business.development.codesupport.service.ICodeSupportService;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "研发支撑/代码生成")
@RestController("devCodeSupportController")
@RequestMapping("/devSupport/codeSupport")
public class CodeSupportController {

    @Autowired
    private ICodeSupportService iCodeSupportService;

    @PostMapping("/selectTableList")
    @ApiOperation(value = "查询数据表信息列表 维护人:Lan StoneElm")
    public ResponseResult<List<TableQueryVO>> selectTableList (
            @ApiParam(name = "TableQueryAO", value = "数据表查询AO") @RequestBody TableQueryAO tableQueryAO) {
        return iCodeSupportService.selectTableList(tableQueryAO);
    }
}
