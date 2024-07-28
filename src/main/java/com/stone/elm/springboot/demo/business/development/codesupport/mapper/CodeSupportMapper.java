package com.stone.elm.springboot.demo.business.development.codesupport.mapper;

import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.TableQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.ao.ColumnQueryAO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.TableQueryVO;
import com.stone.elm.springboot.demo.business.development.codesupport.model.vo.ColumnQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeSupportMapper {

    List<TableQueryVO> selectTableList(TableQueryAO tableQueryAO);

    List<ColumnQueryVO> selectColumnList(ColumnQueryAO columnQueryAO);

    List<ColumnQueryVO> selectTablePK(TableQueryAO tableQueryAO);
}
