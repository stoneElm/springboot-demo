package com.stone.elm.springboot.demo.business.system.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsValAO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsVO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsValVO;

import java.util.List;

public interface ICodeClsService {

    ResponseResult<List<CodeClsVO>> queryCodeClsList(CodeClsAO codeClsAO);

    ResponseResult<List<CodeClsVO>> saveCodeClsList(List<CodeClsAO> codeClsList);

    ResponseResult<List<CodeClsVO>> createCodeClsList(List<CodeClsAO> codeClsList);

    ResponseResult<List<CodeClsVO>> updateCodeClsList(List<CodeClsAO> codeClsList);

    ResponseResult<List<CodeClsVO>> deleteCodeClsList(List<CodeClsAO> codeClsAOList);

    ResponseResult<List<CodeClsValVO>> queryCodeClsValList(CodeClsValAO codeClsValAO);

    ResponseResult<List<CodeClsValVO>> saveCodeClsValList(List<CodeClsValAO> codeClsValList);

    ResponseResult<List<CodeClsValVO>> createCodeClsValList(List<CodeClsValAO> codeClsValList);

    ResponseResult<List<CodeClsValVO>> updateCodeClsValList(List<CodeClsValAO> codeClsValList);

    ResponseResult<List<CodeClsValVO>> deleteCodeClsValList(List<CodeClsValAO> codeClsValList);
}
