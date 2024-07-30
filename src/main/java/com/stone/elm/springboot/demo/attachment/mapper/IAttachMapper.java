package com.stone.elm.springboot.demo.attachment.mapper;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.root.AttachDtlRoot;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAttachMapper {

    Integer createAttachList(@Param("createAttachList") List<AttachAO> createAttachList);

    Integer createAttachDtlList(@Param("createAttachDtlList") List<AttachDtlRoot> createAttachDtlList);

    Integer updateAttachDtlList(@Param("updateAttachDtlList") List<AttachDtlAO> updateAttachDtlList);

    Integer deleteAttachDtlList(@Param("deleteAttachDtlList") List<AttachDtlAO> deleteAttachDtlList);

    List<AttachDtlVO> selectAttachDtlList(AttachDtlAO attachDtlAO);
}
