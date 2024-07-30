package com.stone.elm.springboot.demo.attachment.service;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.root.AttachDtlRoot;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IAttachFileService {

    ResponseResult<List<AttachDtlRoot>> batchUpload(AttachAO attachAO, MultipartFile... files);

    void download(HttpServletRequest request, HttpServletResponse response, AttachDtlAO attachAO);

    void download(HttpServletRequest request, HttpServletResponse response, Long AttachDtlID);

    ResponseEntity<Resource> filePreview(Long attachDtlID);

    ResponseResult<AttachDtlVO> getDownloadUrl(AttachDtlAO attachAO);

    ResponseResult<List<AttachDtlVO>> selectAttachDtlList(AttachDtlAO attachAO);

    ResponseResult<List<AttachDtlVO>> deleteAttachDtlByID(AttachDtlAO attachAO);

    ResponseResult<List<AttachDtlVO>> deleteAttachDtlList(List<AttachDtlAO> deleteAttachDtlList, Boolean deleteFileFlag);

    ResponseResult<List<AttachDtlVO>> updateAttachDtlList(List<AttachDtlAO> updateAttachDtlList);
}
