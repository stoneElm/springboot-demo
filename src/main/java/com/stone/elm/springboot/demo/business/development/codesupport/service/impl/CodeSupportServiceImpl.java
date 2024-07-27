package com.stone.elm.springboot.demo.business.development.codesupport.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.development.codesupport.mapper.CodeSupportMapper;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.ColumnQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.TableQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.ColumnQueryVO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.TableQueryVO;
import com.stone.elm.springboot.demo.business.development.codesupport.service.ICodeSupportService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeSupportServiceImpl implements ICodeSupportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeSupportServiceImpl.class);

    private static final String TAB = "    ";
    private static final String LINE = "\n";
    private static final String QUOTE = "\"";

    @Autowired
    private CodeSupportMapper codeSupportMapper;

    @Override
    public ResponseResult<List<TableQueryVO>> selectTableList(TableQueryAO tableQueryAO) {
        LOGGER.info("查询数据表列表入参:{}", JsonUtil.convertObjectToJson(tableQueryAO));

        List<TableQueryVO> resultData = codeSupportMapper.selectTableList(tableQueryAO);

        ResponseResult<List<TableQueryVO>> result = ResultUtils.wrapResult(resultData);
        LOGGER.info("查询数据表列表出参:{}", JsonUtil.convertObjectToJson(result));

        return result;
    }

    @Override
    public ResponseResult<List<String>> produceCURDCode(ColumnQueryAO columnQueryAO) {
        LOGGER.info("生成代码信息入参:{}", JsonUtil.convertObjectToJson(columnQueryAO));

        if (StringUtils.isBlank(columnQueryAO.getTableName()) || StringUtils.isBlank(columnQueryAO.getOperationType())) {
            throw new BusinessException("数据表名称或！", ResponseConstant.FAIL);
        }

        TableQueryAO tableQueryAO = new TableQueryAO();
        TableQueryVO tableQueryVO = selectTableList(tableQueryAO).getData().stream().findFirst().get();

        List<ColumnQueryVO> columnList = codeSupportMapper.selectColumnList(columnQueryAO);
        LOGGER.info("查询字段信息出参:{}", JsonUtil.convertObjectToJson(columnList));

        StringBuilder resultSB = new StringBuilder();

        switch (columnQueryAO.getOperationType()) {
            case "01":
                resultSB = produceAVOClass(tableQueryVO, columnList, "AO");
                break;
            case "02":
                resultSB = produceAVOClass(tableQueryVO, columnList, "VO");
                break;
            case "03":
                resultSB = produceController(tableQueryVO, columnList);
                break;
            case "04":
                resultSB = produceService(tableQueryVO, columnList);
                break;
            case "05":
                resultSB = produceServiceImpl(tableQueryVO, columnList);
                break;
            case "06":
                resultSB = produceMapper(tableQueryVO, columnList);
                break;
            case "07":
                resultSB = produceMapperXML(tableQueryVO, columnList);
                break;
        }

        ArrayList<String> resultData = new ArrayList<>();
        resultData.add(resultSB.toString());
        ResponseResult<List<String>> result = ResultUtils.wrapResult(resultData);
        LOGGER.info("生成代码信息出参:{}", JsonUtil.convertObjectToJson(result));

        return result;
    }

    private StringBuilder produceAVOClass(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList, String model) {
        StringBuilder resultSB = new StringBuilder();
        resultSB.append("@ApiModel(value = " + QUOTE + tableCapitalStart(tableQueryVO.getTableName()) + model + QUOTE + ", description = " + QUOTE + tableQueryVO.getTableComment() + "查询" + model + QUOTE + ")" + LINE);
        resultSB.append("public class " + tableCapitalStart(tableQueryVO.getTableName()) + model + " {" + LINE);
        resultSB.append(LINE);

        for (ColumnQueryVO columnQueryVO : columnList) {
            resultSB.append(TAB + "@ApiModelProperty(value = " + QUOTE + columnQueryVO.getColumnComment() + QUOTE + ")" + LINE);
            resultSB.append(TAB + "private String " + getHump(columnQueryVO.getColumnName()) + ";" + LINE);
            resultSB.append(LINE);
        }

        resultSB.append("}");

        return resultSB;
    }

    private StringBuilder produceController(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();
        return resultSB;
    }

    private StringBuilder produceService(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();
        return resultSB;
    }

    private StringBuilder produceServiceImpl(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();
        return resultSB;
    }

    private StringBuilder produceMapper(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();
        return resultSB;
    }

    private StringBuilder produceMapperXML(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();
        return resultSB;
    }

    private String getHump(String tableName) {
        String result = SymbolConstant.STRING_BLANK;

        if (StringUtils.isBlank(tableName)) {
            return result;
        }

        tableName = tableName.toLowerCase();
        Boolean idFlag = tableName.indexOf("_id") == tableName.length() - NumberConstant.THREE;
        if (StringUtils.equals("id", tableName) || StringUtils.equals("_id", tableName)) {
            return "id";
        }

        if (tableName.indexOf(SymbolConstant.UNDERLINE) == NumberConstant.NEGATIVE_ONE) {
            return tableName;
        }

        if (idFlag) {
            tableName = tableName.substring(NumberConstant.ZERO, tableName.length() - NumberConstant.THREE);
        }

        String[] split = tableName.split(SymbolConstant.UNDERLINE);

        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                result += split[0];
            } else {
                result += capitalStart(split[i]);
            }
        }

        if (idFlag) {
            result += "ID";
        }

        return result;
    }

    private String capitalStart(String s) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstant.STRING_BLANK;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String tableCapitalStart(String s) {
        return capitalStart(getHump(s));
    }
}
