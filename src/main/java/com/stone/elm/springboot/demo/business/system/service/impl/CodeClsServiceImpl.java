package com.stone.elm.springboot.demo.business.system.service.impl;

import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.ConvertUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsValAO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsVO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsValVO;
import com.stone.elm.springboot.demo.business.system.mapper.CodeClsMapper;
import com.stone.elm.springboot.demo.business.system.model.constant.CodeClsConstant;
import com.stone.elm.springboot.demo.business.system.service.ICodeClsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeClsServiceImpl implements ICodeClsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeClsServiceImpl.class);

    @Autowired
    private CodeClsMapper codeClsMapper;

    @Override
    public ResponseResult<List<CodeClsVO>> queryCodeClsList(CodeClsAO codeClsAO) {
        LOGGER.info("查询标准代码分类列表总体入参:{}", JsonUtil.convertObjectToJson(codeClsAO));

        List<CodeClsVO> resultData = codeClsMapper.getCodeClsList(codeClsAO);
        Integer countCodeClsAll = codeClsMapper.countCodeClsAll(codeClsAO);

        ResponseResult<List<CodeClsVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countCodeClsAll);

        LOGGER.info("查询标准代码分类列表总体出参:{}", JsonUtil.convertObjectToJson(result));

        return result;
    }

    @Override
    public ResponseResult<List<CodeClsValVO>> queryCodeClsValList(CodeClsValAO codeClsValAO) {
        LOGGER.info("查询标准代码分类值总体入参:{}", JsonUtil.convertObjectToJson(codeClsValAO));

        List<CodeClsValVO> resultData = codeClsMapper.getCodeClsValList(codeClsValAO);
        Integer countCodeClsValAll = codeClsMapper.countCodeClsValAll(codeClsValAO);

        ResponseResult<List<CodeClsValVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countCodeClsValAll);

        LOGGER.info("查询标准代码分类值总体出参:{}", JsonUtil.convertObjectToJson(result));

        return result;
    }

    @Override
    public ResponseResult<List<CodeClsVO>> saveCodeClsList(List<CodeClsAO> codeClsList) {
        LOGGER.info("保存标准代码分类总体入参:{}", JsonUtil.convertObjectToJson(codeClsList));

        if (CollectionUtils.isEmpty(codeClsList)) {
            return ResultUtils.failResult("请上传需要保存的数据");
        }

        List<CodeClsAO> createCodeClsList = codeClsList.stream().filter(codeCls -> codeCls.getCodeClsID() == null).collect(Collectors.toList());
        List<CodeClsAO> updateCodeClsList = codeClsList.stream().filter(codeCls -> codeCls.getCodeClsID() != null).collect(Collectors.toList());
        LOGGER.info("保存获取到新增标准代码分类列表信息:{}", JsonUtil.convertObjectToJson(createCodeClsList));
        LOGGER.info("保存获取到更新标准代码分类总体入参:{}", JsonUtil.convertObjectToJson(updateCodeClsList));

        if (CollectionUtils.isNotEmpty(createCodeClsList)) {
            createCodeClsList(createCodeClsList);
        }

        if (CollectionUtils.isNotEmpty(updateCodeClsList)) {
            updateCodeClsList(updateCodeClsList);
        }

        ResponseResult<List<CodeClsVO>> result = ResultUtils.wrapResult(null);

        LOGGER.info("保存标准代码分类总体出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<CodeClsValVO>> saveCodeClsValList(List<CodeClsValAO> codeClsValList) {
        LOGGER.info("保存标准代码分类值入参:{}", JsonUtil.convertObjectToJson(codeClsValList));

        if (CollectionUtils.isEmpty(codeClsValList)) {
            return ResultUtils.failResult("请上传需要保存的数据");
        }

        List<CodeClsValAO> createCodeClsValList = codeClsValList.stream().filter(codeClsVal -> codeClsVal.getCodeClsValID() == null).collect(Collectors.toList());
        List<CodeClsValAO> updateCodeClsValList = codeClsValList.stream().filter(codeClsVal -> codeClsVal.getCodeClsValID() != null).collect(Collectors.toList());
        LOGGER.info("新增标准代码值总体入参:{}", JsonUtil.convertObjectToJson(createCodeClsValList));
        LOGGER.info("更新标准代码值总体入参:{}", JsonUtil.convertObjectToJson(updateCodeClsValList));

        if (CollectionUtils.isNotEmpty(createCodeClsValList)) {
            createCodeClsValList(createCodeClsValList);
        }

        if (CollectionUtils.isNotEmpty(updateCodeClsValList)) {
            updateCodeClsValList(updateCodeClsValList);
        }

        ResponseResult<List<CodeClsValVO>> result = ResultUtils.wrapResult(null);

        LOGGER.info("保存标准代码分类总体出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<CodeClsVO>> createCodeClsList(List<CodeClsAO> createCodeClsList) {
        LOGGER.info("新增标准代码分类总体入参:{}", JsonUtil.convertObjectToJson(createCodeClsList));

        if (CollectionUtils.isEmpty(createCodeClsList)) {
            return ResultUtils.wrapResult();
        }

        // 新增前校验标准代码分类类型不能重复
        String checkResult = checkCreateCodeClsListParam(createCodeClsList);

        if (StringUtils.isNotBlank(checkResult)) {
            return ResultUtils.failResult(checkResult, ResponseConstant.VALIDATION_FAILED);
        }

        Integer row = codeClsMapper.createCodeClsList(createCodeClsList);

        LOGGER.info("成功执行{}条数据", row);

        return ResultUtils.wrapResult();
    }

    private String checkCreateCodeClsListParam(List<CodeClsAO> createCodeClsList) {
        LOGGER.info("--- 新增标准代码分类信息校验入参开始 ---");

        List<String> createCodeClsTypeList = createCodeClsList.stream().map(CodeClsAO::getCodeClsType).distinct().collect(Collectors.toList());

        if (createCodeClsTypeList.size() != createCodeClsList.size()) {
            return "存在标准代码分类类型重复的数据";
        }

        CodeClsAO codeClsAO = new CodeClsAO();
        codeClsAO.setCodeClsTypeList(createCodeClsTypeList);

        ResponseResult<List<CodeClsVO>> queryResult = queryCodeClsList(codeClsAO);

        if (CollectionUtils.isEmpty(queryResult.getData())) {
            return null;
        }

        StringBuilder result = new StringBuilder("已存在标准代码分类类型为：");

        for (CodeClsVO datum : queryResult.getData()) {
            result.append("【").append(datum.getCodeClsType()).append("】");
        }

        result.append("的标准代码分类信息！");

        LOGGER.info("--- 新增标准代码分类信息校验入参结束 ---");
        return StringUtils.isBlank(result)? null: result.toString();
    }

    @Override
    public ResponseResult<List<CodeClsVO>> updateCodeClsList(List<CodeClsAO> updateCodeClsList) {
        LOGGER.info("更新标准代码分类总体入参:{}", JsonUtil.convertObjectToJson(updateCodeClsList));

        if (CollectionUtils.isEmpty(updateCodeClsList)) {
            return ResultUtils.wrapResult();
        }

        // 更新前校验是否可以被更新
        String checkUpdateFlagResult = checkUpdateFlag(updateCodeClsList);

        if (StringUtils.isNotBlank(checkUpdateFlagResult)) {
            return ResultUtils.failResult(checkUpdateFlagResult, ResponseConstant.VALIDATION_FAILED);
        }

        // 更新前校验标准代码分类类型不能重复
        String checkResult = checkUpdateCodeClsListParam(updateCodeClsList);

        if (StringUtils.isNotBlank(checkResult)) {
            return ResultUtils.failResult(checkResult, ResponseConstant.VALIDATION_FAILED);
        }

        Integer row = codeClsMapper.updateCodeClsList(updateCodeClsList);

        LOGGER.info("成功执行{}条数据", row);

        return ResultUtils.wrapResult();
    }

    private String checkUpdateCodeClsListParam(List<CodeClsAO> updateCodeClsList) {
        LOGGER.info("--- 更新标准代码分类信息校验入参开始 ---");

        List<Long> updateCodeClsIDList = updateCodeClsList.stream().map(CodeClsAO::getCodeClsID).distinct().collect(Collectors.toList());

        if (updateCodeClsIDList.size() != updateCodeClsList.size()) {
            return "每次更新同一标准代码分类只能存在唯一数据";
        }

        List<String> updateCodeClsTypeList = updateCodeClsList.stream().map(CodeClsAO::getCodeClsType).distinct().collect(Collectors.toList());

        if (updateCodeClsTypeList.size() != updateCodeClsList.size()) {
            return "存在标准代码分类类型重复的数据";
        }

        CodeClsAO codeClsAO = new CodeClsAO();
        codeClsAO.setNotCodeClsIDList(updateCodeClsIDList);
        codeClsAO.setCodeClsTypeList(updateCodeClsTypeList);

        ResponseResult<List<CodeClsVO>> queryResult = queryCodeClsList(codeClsAO);

        if (CollectionUtils.isEmpty(queryResult.getData())) {
            return null;
        }

        // 取出所有存在的类型
        List<String> existCodeClsTypeList = queryResult.getData().stream().map(CodeClsVO::getCodeClsType).distinct().collect(Collectors.toList());

        ArrayList<String> repeatData = new ArrayList<>();

        for (String codeClsType : updateCodeClsTypeList) {
            if (existCodeClsTypeList.contains(codeClsType)) {
                repeatData.add(codeClsType);
            }
        }

        if (CollectionUtils.isEmpty(repeatData)) {
            return null;
        }

        StringBuilder result = new StringBuilder("已存在标准代码分类类型为：");

        for (String codeClsType : repeatData) {
            result.append("【").append(codeClsType).append("】");
        }

        result.append("的标准代码分类信息！");

        LOGGER.info("--- 更新标准代码分类信息校验入参结束 ---");
        return StringUtils.isBlank(result)? null: result.toString();
    }

    @Override
    public ResponseResult<List<CodeClsValVO>> createCodeClsValList(List<CodeClsValAO> createCodeClsValList) {
        LOGGER.info("新增标准代码值总体入参:{}", JsonUtil.convertObjectToJson(createCodeClsValList));

        if (CollectionUtils.isEmpty(createCodeClsValList)) {
            return ResultUtils.wrapResult();
        }

        checkCodeClsValUpdateFlag(createCodeClsValList);

        Integer row = codeClsMapper.createCodeClsValList(createCodeClsValList);

        LOGGER.info("成功执行{}条数据", row);

        return ResultUtils.wrapResult();
    }

    private void checkCodeClsValUpdateFlag(List<CodeClsValAO> createCodeClsValList) {
        List<Long> codeClsIDList = createCodeClsValList.stream().map(CodeClsValAO::getCodeClsID).distinct().collect(Collectors.toList());
        CodeClsAO codeClsAO = new CodeClsAO();
        codeClsAO.setCodeClsIDList(codeClsIDList);
        codeClsAO.setUpdateFlag(CodeClsConstant.UPDATE_FLAG_NO);
        List<CodeClsVO> codeClsList = codeClsMapper.getCodeClsList(codeClsAO);

        if (CollectionUtils.isEmpty(codeClsList)) {
            return;
        }

        StringBuilder result = new StringBuilder("存在标准代码分类为：");

        for (CodeClsVO datum : codeClsList) {
            result.append("【").append(datum.getCodeClsType()).append("】");
        }

        result.append("下的标准代码值信息不允许被修改！");

        throw new BusinessException(result.toString(), ResponseConstant.VALIDATION_FAILED);
    }

    @Override
    public ResponseResult<List<CodeClsValVO>> updateCodeClsValList(List<CodeClsValAO> updateCodeClsValList) {
        LOGGER.info("更新标准代码分类值入参:{}", JsonUtil.convertObjectToJson(updateCodeClsValList));

        if (CollectionUtils.isEmpty(updateCodeClsValList)) {
            return ResultUtils.wrapResult();
        }

        checkCodeClsValUpdateFlag(updateCodeClsValList);

        Integer row = codeClsMapper.updateCodeClsValList(updateCodeClsValList);

        LOGGER.info("成功执行{}条数据", row);

        return ResultUtils.wrapResult();
    }

    @Override
    public ResponseResult<List<CodeClsVO>> deleteCodeClsList(List<CodeClsAO> deleteCodeClsList) {
        LOGGER.info("删除标准代码分类值入参:{}", JsonUtil.convertObjectToJson(deleteCodeClsList));

        if (CollectionUtils.isEmpty(deleteCodeClsList)) {
            return ResultUtils.wrapResult();
        }

        // 更新前校验是否可以被更新
        String checkUpdateFlagResult = checkUpdateFlag(deleteCodeClsList);

        if (StringUtils.isNotBlank(checkUpdateFlagResult)) {
            return ResultUtils.failResult(checkUpdateFlagResult, ResponseConstant.VALIDATION_FAILED);
        }

        Integer row = codeClsMapper.deleteCodeClsList(deleteCodeClsList);
        LOGGER.info("成功执行{}条数据", row);

        // 同步删除子表数据
        List<Long> deleteCodeClsIDList = deleteCodeClsList.stream().map(CodeClsAO::getCodeClsID).distinct().collect(Collectors.toList());

        CodeClsValAO codeClsValAO = new CodeClsValAO();
        codeClsValAO.setCodeClsIDList(deleteCodeClsIDList);

        ResponseResult<List<CodeClsValVO>> queryCodeClsValResponse = queryCodeClsValList(codeClsValAO);
        List<CodeClsValVO> queryCodeClsValList = queryCodeClsValResponse.getData();

        if (CollectionUtils.isEmpty(queryCodeClsValList)) {
            LOGGER.info("删除标准代码分类-无标准代码值信息");
            return ResultUtils.wrapResult();
        }

        List<CodeClsValAO> deleteCodeClsValList = ConvertUtil.convertListFromListIgnoreNull(queryCodeClsValList, CodeClsValAO.class);

        this.deleteCodeClsValList(deleteCodeClsValList);

        return ResultUtils.wrapResult();
    }

    private String checkUpdateFlag(List<CodeClsAO> updateCodeClsList) {
        LOGGER.info("--- 是否可以被更新校验入参开始 ---");

        List<Long> updateIDList = updateCodeClsList.stream().map(CodeClsAO::getCodeClsID).distinct().collect(Collectors.toList());
        CodeClsAO codeClsAO = new CodeClsAO();
        codeClsAO.setCodeClsIDList(updateIDList);
        codeClsAO.setUpdateFlag(CodeClsConstant.UPDATE_FLAG_NO);

        ResponseResult<List<CodeClsVO>> codeClsListResult = this.queryCodeClsList(codeClsAO);
        List<CodeClsVO> codeClsList = codeClsListResult.getData();

        if (CollectionUtils.isEmpty(codeClsList)) {
            LOGGER.info("--- 是否可以被更新校验入参结束 ---");
            return null;
        }

        StringBuilder result = new StringBuilder("存在标准代码分类类型为：");

        for (CodeClsVO codeClsVO : codeClsList) {
            result.append("【").append(codeClsVO.getCodeClsType()).append("】");
        }

        result.append("的标准代码分类信息不允许被删除或修改！");

        LOGGER.info("--- 是否可以被更新校验入参结束 ---");
        return StringUtils.isBlank(result)? null: result.toString();
    }

    @Override
    public ResponseResult<List<CodeClsValVO>> deleteCodeClsValList(List<CodeClsValAO> deleteCodeClsValList) {
        LOGGER.info("删除标准代码分类值入参:{}", JsonUtil.convertObjectToJson(deleteCodeClsValList));

        if (CollectionUtils.isEmpty(deleteCodeClsValList)) {
            return ResultUtils.wrapResult();
        }

        checkCodeClsValUpdateFlag(deleteCodeClsValList);

        Integer row = codeClsMapper.deleteCodeClsValList(deleteCodeClsValList);

        LOGGER.info("成功执行{}条数据", row);

        return ResultUtils.wrapResult();
    }


}
