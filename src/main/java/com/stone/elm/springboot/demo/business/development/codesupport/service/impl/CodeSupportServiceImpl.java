package com.stone.elm.springboot.demo.business.development.codesupport.service.impl;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.business.development.codesupport.mapper.CodeSupportMapper;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.TableQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.TableQueryVO;
import com.stone.elm.springboot.demo.business.development.codesupport.service.ICodeSupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeSupportServiceImpl implements ICodeSupportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeSupportServiceImpl.class);

    @Autowired
    private CodeSupportMapper codeSupportMapper;

    @Override
    public ResponseResult<List<TableQueryVO>> selectTableList(TableQueryAO tableQueryAO) {

        List<TableQueryVO> resultData = codeSupportMapper.selectTableList(tableQueryAO);

        resultData.addAll(resultData);
        resultData.addAll(resultData);
        resultData.addAll(resultData);

        return ResultUtils.wrapResult(resultData);
    }
}
