package com.stone.elm.springboot.demo.business.system.mapper;

import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsValAO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsVO;
import com.stone.elm.springboot.demo.business.system.model.vo.CodeClsValVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeClsMapper {

    List<CodeClsVO> getCodeClsList(CodeClsAO codeClsAO);

    Integer countCodeClsAll(CodeClsAO codeClsAO);

    Integer createCodeClsList(@Param("createCodeClsList") List<CodeClsAO> createCodeClsList);

    Integer updateCodeClsList(@Param("updateCodeClsList") List<CodeClsAO> updateCodeClsList);

    Integer deleteCodeClsList(@Param("deleteCodeClsList") List<CodeClsAO> deleteCodeClsList);

    List<CodeClsValVO> getCodeClsValList(CodeClsValAO codeClsValAO);

    Integer countCodeClsValAll(CodeClsValAO codeClsValAO);

    Integer createCodeClsValList(@Param("createCodeClsValList") List<CodeClsValAO> createCodeClsValList);

    Integer updateCodeClsValList(@Param("updateCodeClsValList") List<CodeClsValAO> updateCodeClsValList);

    Integer deleteCodeClsValList(@Param("deleteCodeClsValList") List<CodeClsValAO> deleteCodeClsValList);
}
