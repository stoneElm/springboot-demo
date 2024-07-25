package com.stone.elm.springboot.demo.business.development.codesupport.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.TableQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.TableQueryVO;

import java.util.List;

public interface ICodeSupportService {

    ResponseResult<List<TableQueryVO>> selectTableList(TableQueryAO tableQueryAO);
}
