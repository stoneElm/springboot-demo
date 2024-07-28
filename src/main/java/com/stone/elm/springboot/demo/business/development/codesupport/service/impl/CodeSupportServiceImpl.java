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
        tableQueryAO.setTableName(columnQueryAO.getTableName());
        TableQueryVO tableQueryVO = selectTableList(tableQueryAO).getData().stream().findFirst().get();

        List<ColumnQueryVO> columnList = codeSupportMapper.selectColumnList(columnQueryAO);
        LOGGER.info("查询字段信息出参:{}", JsonUtil.convertObjectToJson(columnList));

        String tablePK = codeSupportMapper.selectTablePK(tableQueryAO).stream().findFirst().get().getColumnName();
        LOGGER.info("查询主键信息出参:{}", tablePK);

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
                resultSB = produceServiceImpl(tableQueryVO, tablePK);
                break;
            case "06":
                resultSB = produceMapper(tableQueryVO, columnList);
                break;
            case "07":
                resultSB = produceMapperXML(tableQueryVO, tablePK, columnList);
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
        resultSB.append("@Api(tags = " + QUOTE + "_____/" + tableQueryVO.getTableComment() + "维护" + QUOTE + ")" + LINE);
        resultSB.append("@RestController(" + QUOTE + tableCapitalStart(tableQueryVO.getTableName()) + "Controller" + QUOTE + ")" + LINE);
        resultSB.append("@RequestMapping(" + QUOTE + "/_____/" + getHump(tableQueryVO.getTableName()) + QUOTE + ")" + LINE);
        resultSB.append("public class " +  tableCapitalStart(tableQueryVO.getTableName()) + "Controller {" + LINE);
        resultSB.append(TAB + "@Autowired" + LINE);
        resultSB.append(TAB + "private I" + tableCapitalStart(tableQueryVO.getTableName()) + "Service i" + tableCapitalStart(tableQueryVO.getTableName()) + "Service;" + LINE);
        resultSB.append(LINE);

        resultSB.append(getSelectController(tableQueryVO, "select", "查询"));
        resultSB.append(getSelectController(tableQueryVO, "create", "新增"));
        resultSB.append(getSelectController(tableQueryVO, "update", "更新"));
        resultSB.append(getSelectController(tableQueryVO, "delete", "删除"));

        resultSB.append("}");
        return resultSB;
    }

    private StringBuilder produceService(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());

        resultSB.append("public interface I" + var1 + "Service {" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("查询" + tableQueryVO.getTableComment() + "列表Service", var2 + "AO"));
        resultSB.append(TAB + "ResponseResult<List<" + var1 + "VO>> select" + var1 + "List(" + var1 + "AO " + var2 + "AO);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("创建" + tableQueryVO.getTableComment() + "列表Service", var2 + "List"));
        resultSB.append(TAB + "ResponseResult<List<" + var1 + "VO>> create" + var1 + "List(List<" + var1 + "AO> " + var2 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("更新" + tableQueryVO.getTableComment() + "列表Service", var2 + "List"));
        resultSB.append(TAB + "ResponseResult<List<" + var1 + "VO>> update" + var1 + "List(List<" + var1 + "AO> " + var2 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("删除" + tableQueryVO.getTableComment() + "列表Service", var2 + "List"));
        resultSB.append(TAB + "ResponseResult<List<" + var1 + "VO>> delete" + var1 + "List(List<" + var1 + "AO> " + var2 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append("}");

        return resultSB;
    }

    private StringBuilder produceServiceImpl(TableQueryVO tableQueryVO, String tablePK) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());

        resultSB.append("@Service" + LINE);
        resultSB.append("public class " + var1 + "ServiceImpl implements I" + var1 + "Service {" + LINE);
        resultSB.append(TAB + "private static final Logger LOGGER = LoggerFactory.getLogger(" + var1 + "ServiceImpl.class);" + LINE);
        resultSB.append(LINE);
        resultSB.append(TAB + "@Autowired" + LINE + TAB + "private IPrimaryKeyService iPrimaryKeyService;" + LINE);
        resultSB.append(LINE);
        resultSB.append(TAB + "@Autowired" + LINE + TAB + "private " + var1 + "Mapper " + var2 + "Mapper;" + LINE);
        resultSB.append(LINE);

        resultSB.append(proAnnotate("查询" + tableQueryVO.getTableComment() + "列表ServiceImpl", var2 + "AO"));
        resultSB.append(proImplCode(tableQueryVO, tablePK, "select", "查询"));

        resultSB.append(proAnnotate("创建" + tableQueryVO.getTableComment() + "列表ServiceImpl", "create" + var1 + "List"));
        resultSB.append(proImplCode(tableQueryVO, tablePK, "create", "创建"));

        resultSB.append(proAnnotate("更新" + tableQueryVO.getTableComment() + "列表ServiceImpl", "update" + var1 + "List"));
        resultSB.append(proImplCode(tableQueryVO, tablePK, "update", "更新"));

        resultSB.append(proAnnotate("删除" + tableQueryVO.getTableComment() + "列表ServiceImpl", "delete" + var1 + "List"));
        resultSB.append(proImplCode(tableQueryVO, tablePK, "delete", "删除"));

        resultSB.append("}");
        return resultSB;
    }

    private String proImplCode(TableQueryVO tableQueryVO, String tablePK, String type, String desc) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());
        String var3 = StringUtils.equals("select", type)? var1 + "AO": "List<" + var1 + "AO>";
        String var4 = StringUtils.equals("select", type)? var2 + "AO": type + var1 + "List";

        resultSB.append(TAB + "@Override" + LINE);
        resultSB.append(TAB + "public ResponseResult<List<" + var1 + "VO>> " + type + var1 + "List(" + var3 + " " + var4 + ") {" + LINE);
        resultSB.append(TAB + TAB + "LOGGER.info(" + QUOTE + desc + tableQueryVO.getTableComment() + "入参:{}" + QUOTE + ", JsonUtil.convertObjectToJson(" + var4 + "));" + LINE);
        resultSB.append(LINE);

        if (!StringUtils.equals("select", type)) {
            resultSB.append(TAB + TAB + "if (CollectionUtils.isEmpty(" + type + var1 + "List)) {" + LINE);
            resultSB.append(TAB + TAB + TAB + "return ResultUtils.wrapResult();" + LINE);
            resultSB.append(TAB + TAB + "}" + LINE);
            resultSB.append(LINE);
        }

        if (StringUtils.equals("create", type)) {
            resultSB.append(TAB + TAB + "for (" + var1 + "AO " + var2 + "AO : " + var4 + ") {" + LINE);
            resultSB.append(TAB + TAB + TAB + var2 + "AO.set" + tableCapitalStart(tablePK) + "(iPrimaryKeyService.getPrimaryKey());" + LINE);
            resultSB.append(TAB + TAB + "}" + LINE);
            resultSB.append(LINE);
        }

        if (!StringUtils.equals("select", type)) {
            resultSB.append(TAB + TAB + "Integer row = " + var2 + "Mapper." + type + var1 + "List(" + var4 + ");" + LINE);
            resultSB.append(TAB + TAB + "LOGGER.info(" + QUOTE + "成功执行{}条数据" + QUOTE + ", row);" + LINE);
            resultSB.append(LINE);
            resultSB.append(TAB + TAB + "List<" + var1 + "VO> resultData = new ArrayList<>();" + LINE);
            resultSB.append(TAB + TAB + "BeanCopyUtil.copyList(" + var4 + ", resultData, " + var1 + "VO.class);" + LINE);
            resultSB.append(LINE);
        } else {
            resultSB.append(TAB + TAB + "List<" + var1 + "VO> resultData = codeClsMapper." + type + var1 + "List(" + var2+ "AO);" + LINE);
            resultSB.append(TAB + TAB + "LOGGER.info(" + QUOTE + "根据条件查询" + tableQueryVO.getTableComment() + "信息列表出差:{}" + QUOTE + ", JsonUtil.convertObjectToJson(resultData));" + LINE);
            resultSB.append(LINE);

            resultSB.append(TAB + TAB + "Integer count" + var1 + "All = " + var2 + "Mapper.count" + var1 + "All(" + var2 + "AO);" + LINE);
            resultSB.append(TAB + TAB + "LOGGER.info(" + QUOTE + "根据条件统计结果:{}" + QUOTE + ", count" + var1 + "All);" + LINE);
            resultSB.append(LINE);
        }


        resultSB.append(TAB + TAB + "ResponseResult<List<" + var1 + "VO>> result = ResultUtils.wrapResult(resultData);" + LINE);

        if (StringUtils.equals("select", type)) {
            resultSB.append(TAB + TAB + "result.setTotal(count" + var1 + "All);" + LINE);
        }

        resultSB.append(LINE);

        resultSB.append(TAB + TAB + "LOGGER.info(" + QUOTE + desc + tableQueryVO.getTableComment() + "出参:{}" + QUOTE + ", JsonUtil.convertObjectToJson(result));" + LINE);
        resultSB.append(TAB + TAB + "return result;" + LINE);
        resultSB.append(TAB + "}" + LINE);
        resultSB.append(LINE);
        return resultSB.toString();
    }

    private StringBuilder produceMapper(TableQueryVO tableQueryVO, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());

        resultSB.append("@Mapper" + LINE + "public interface " + var1 + "Mapper {");
        resultSB.append(LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("查询" + tableQueryVO.getTableComment() + "列表Mapper", var2 + "AO"));
        resultSB.append(TAB + "List<" + var1 + "VO> select" + var1 + "List(" + var1 + "AO " + var2 + "AO);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("统计" + tableQueryVO.getTableComment() + "列表Mapper", var2 + "AO"));
        resultSB.append(TAB + "Integer count" + var1 + "All(" + var1 + "AO " + var2 + "AO);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("创建" + tableQueryVO.getTableComment() + "列表Mapper", "create" + var1 + "List"));
        resultSB.append(TAB + "Integer create" + var1 + "List(@Param(" + QUOTE + "create" + var1 + "List" + QUOTE + ") List<" + var1 + "AO> create" + var1 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("修改" + tableQueryVO.getTableComment() + "列表Mapper", "update" + var1 + "List"));
        resultSB.append(TAB + "Integer update" + var1 + "List(@Param(" + QUOTE + "update" + var1 + "List" + QUOTE + ") List<" + var1 + "AO> update" + var1 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append(proAnnotate("删除" + tableQueryVO.getTableComment() + "列表Mapper", "delete" + var1 + "List"));
        resultSB.append(TAB + "Integer delete" + var1 + "List(@Param(" + QUOTE + "delete" + var1 + "List" + QUOTE + ") List<" + var1 + "AO> delete" + var1 + "List);" + LINE);
        resultSB.append(LINE);
        resultSB.append("}");
        return resultSB;
    }

    private StringBuilder produceMapperXML(TableQueryVO tableQueryVO, String tablePK, List<ColumnQueryVO> columnList) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());

        resultSB.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE);
        resultSB.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" + LINE);
        resultSB.append("<mapper namespace=\"com.stone.elm.springboot.demo._____._____.mapper." + var1 + "Mapper\" >" + LINE);
        resultSB.append(LINE);
        resultSB.append(TAB + "<sql id=\"" + var2 + "InsertFields\">" + LINE);
        for (int i = 0; i < columnList.size(); i++) {
            resultSB.append(TAB + TAB + columnList.get(i).getColumnName());
            if (i != columnList.size() - 1) {
                resultSB.append(",");
            }
            resultSB.append(LINE);
        }
        resultSB.append(TAB + "</sql>" + LINE + LINE);
        resultSB.append(TAB + "<sql id=\"" + var2 + "QueryFields\">" + LINE);
        for (int i = 0; i < columnList.size(); i++) {
            resultSB.append(TAB + TAB + columnList.get(i).getColumnName() + " as \"" + getHump(columnList.get(i).getColumnName()) + "\"");
            if (i != columnList.size() - 1) {
                resultSB.append(",");
            }
            resultSB.append(LINE);
        }
        resultSB.append(TAB + "</sql>" + LINE + LINE);
        resultSB.append(TAB + "<sql id=\"codeClsQueryParam\">\n" + TAB + TAB + "<where>" + LINE);
        for (int i = 0; i < columnList.size(); i++) {
            resultSB.append(TAB + TAB + TAB + "<if test=\"" + getHump(columnList.get(i).getColumnName()) + " != null and " + getHump(columnList.get(i).getColumnName()) + " != ''\">" + LINE);
            resultSB.append(TAB + TAB + TAB + TAB + "and t." + columnList.get(i).getColumnName() + " = #{" + getHump(columnList.get(i).getColumnName()) + "}" + LINE);
            resultSB.append(TAB + TAB + TAB + "</if>" + LINE);
        }
        resultSB.append(TAB + TAB + "</where>\n" + TAB + "</sql>" + LINE + LINE);

        resultSB.append(TAB + "<sql id=\"pageParam\">" + LINE);
        resultSB.append(TAB + TAB + "<if test=\"pageSize != null and pageSize != '' and pageNo != null and pageNo != ''\">" + LINE);
        resultSB.append(TAB + TAB + TAB + "limit #{indexStart}, #{pageSize}" + LINE);
        resultSB.append(TAB + TAB + "</if>" + LINE);
        resultSB.append(TAB + "</sql>" + LINE);
        resultSB.append(LINE);

        resultSB.append(TAB + "<select id=\"select" + var1 + "List\" resultType=\"com.stone.elm.springboot.demo._____._____.model.vo." + var1 + "VO\">" + LINE);
        resultSB.append(TAB + TAB + "select" + LINE);
        resultSB.append(TAB + TAB + TAB + "<include refid=\"" + var2 + "QueryFields\"/>" + LINE);
        resultSB.append(TAB + TAB + "from " + tableQueryVO.getTableName() + " t" + LINE);
        resultSB.append(TAB + TAB + "<include refid=\"" + var2 + "QueryParam\"/>" + LINE);
        resultSB.append(TAB + TAB + "<include refid=\"pageParam\"/>" + LINE);
        resultSB.append(TAB + "</select>" + LINE + LINE);

        resultSB.append(TAB + "<select id=\"count" + var1 + "All\" resultType=\"java.lang.Integer\">" + LINE);
        resultSB.append(TAB + TAB + "select count(1)" + LINE);
        resultSB.append(TAB + TAB + "from " + tableQueryVO.getTableName() + " t" + LINE);
        resultSB.append(TAB + TAB + "<include refid=\"" + var2 + "QueryParam\"/>" + LINE);
        resultSB.append(TAB + "</select>" + LINE + LINE);

        resultSB.append(TAB + "<insert id=\"create" + var1 + "List\">" + LINE);
        resultSB.append(TAB + TAB + "insert into " + tableQueryVO.getTableName() + "(<include refid=\"" + var2 + "InsertFields\"/>) values" + LINE);
        resultSB.append(TAB + TAB + "<foreach collection=\"create" + var1 + "List\" separator=\",\" item=\"item\" index=\"index\">" + LINE);
        resultSB.append(TAB + TAB + TAB + "(" + LINE);
        for (int i = 0; i < columnList.size(); i++) {
            resultSB.append(TAB + TAB + TAB + "#{item." + getHump(columnList.get(i).getColumnName()) + "}");
            if (i != columnList.size() - 1) {
                resultSB.append(",");
            }
            resultSB.append(LINE);
        }
        resultSB.append(TAB + TAB + TAB + ")" + LINE);
        resultSB.append(TAB + TAB + "</foreach>" + LINE);
        resultSB.append(TAB + "</insert>" + LINE + LINE);

        resultSB.append(TAB + "<update id=\"update" + var1 + "List\">" + LINE);
        resultSB.append(TAB + TAB + "<foreach collection=\"update" + var1 + "List\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">" + LINE);
        resultSB.append(TAB + TAB + TAB + "update " + tableQueryVO.getTableName() + LINE);
        resultSB.append(TAB + TAB + TAB + "<set>" + LINE);
        for (int i = 0; i < columnList.size(); i++) {
            if (!StringUtils.equals(columnList.get(i).getColumnName(), tablePK)) {
                resultSB.append(TAB + TAB + TAB + TAB + "<if test=\"item." + getHump(columnList.get(i).getColumnName()) + " != null and item." + getHump(columnList.get(i).getColumnName()) + " != ''\">" + LINE);
                resultSB.append(TAB + TAB + TAB + TAB + TAB + columnList.get(i).getColumnName() + " = #{item." + getHump(columnList.get(i).getColumnName()) + "}," + LINE);
                resultSB.append(TAB + TAB + TAB + TAB + "</if>" + LINE);
            }
        }
        resultSB.append(TAB + TAB + TAB + TAB + tablePK + " = #{item." + getHump(tablePK) + "}" + LINE);
        resultSB.append(TAB + TAB + TAB + "</set>" + LINE);
        resultSB.append(TAB + TAB + TAB + "<where>" + LINE);
        resultSB.append(TAB + TAB + TAB + TAB + tablePK + " = #{item." + getHump(tablePK) + "}" + LINE);
        resultSB.append(TAB + TAB + TAB + "</where>" + LINE);
        resultSB.append(TAB + TAB + "</foreach>" + LINE);
        resultSB.append(TAB + "</update>" + LINE + LINE);

        resultSB.append(TAB + "<delete id=\"delete" + var1 + "List\">" + LINE);
        resultSB.append(TAB + TAB + "delete from " + tableQueryVO.getTableName() + " where " + tablePK + " in (" + LINE);
        resultSB.append(TAB + TAB + "<foreach collection=\"delete" + var1 + "List\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\",\">" + LINE);
        resultSB.append(TAB + TAB + TAB + "#{item." + getHump(tablePK) + "}" + LINE);
        resultSB.append(TAB + TAB + "</foreach>" + LINE);
        resultSB.append(TAB + TAB + ")" + LINE);
        resultSB.append(TAB + "</delete>" + LINE + LINE);

        resultSB.append("</mapper>");
        return resultSB;
    }

    private String getSelectController(TableQueryVO tableQueryVO, String type, String desc) {
        StringBuilder resultSB = new StringBuilder();

        String var1 = tableCapitalStart(tableQueryVO.getTableName());
        String var2 = getHump(tableQueryVO.getTableName());

        String ao = StringUtils.equals(type, "select")? var1 + "AO " + var2 + "AO":
                "@Validated(" + var1 + "AO." + type + "Group.class) ValidList<" + var1 + "AO> " + var2 + "List";

        String param = StringUtils.equals(type, "select")? "AO": "List";

        resultSB.append(TAB + "@PostMapping(" + QUOTE + "/" + type + var1 + "List" + QUOTE + ")" + LINE);
        resultSB.append(TAB + "@ApiOperation(value = " + QUOTE + desc + tableQueryVO.getTableComment() + "列表 维护人:Lan StoneElm" + QUOTE + ")" + LINE);
        resultSB.append(TAB + "public ResponseResult<List<" + var1 + "VO>> " + type + var1 + "List (" + LINE);
        resultSB.append(TAB + TAB + TAB + "@ApiParam(name = " + QUOTE + var1 + "AO" + QUOTE + ", value = " + QUOTE + tableQueryVO.getTableComment() + "实体AO" + QUOTE + ") @RequestBody " + ao + ") {" + LINE);
        resultSB.append(TAB + TAB + "return i" + var1 + "Service." + type + var1 + "List(" + var2 + param + ");" + LINE);
        resultSB.append(TAB + "}" + LINE);
        resultSB.append(LINE);
        return resultSB.toString();
    }

    private Object proAnnotate(String desc, String var) {
        StringBuilder resultSB = new StringBuilder();
        resultSB.append(TAB + "/**" + LINE);
        resultSB.append(TAB + " * " + desc + LINE);
        resultSB.append(TAB + " * @param " + var + LINE);
        resultSB.append(TAB + " * @return"+ LINE);
        resultSB.append(TAB + " */" + LINE);
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
