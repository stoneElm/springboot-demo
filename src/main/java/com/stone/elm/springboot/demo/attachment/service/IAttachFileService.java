package com.stone.elm.springboot.demo.attachment.service;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachVO;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IAttachFileService {

    ResponseResult<List<AttachVO>> batchUpload(AttachAO attachAO, MultipartFile... files);

    ResponseEntity<Resource> download(HttpServletRequest request, HttpServletResponse response, AttachDtlAO attachAO);

    ResponseEntity<Resource> download(Long AttachDtlID);

    ResponseEntity<Resource> video(Long attachDtlID);

    ResponseResult<AttachDtlVO> getDownloadUrl(AttachDtlAO attachAO);
}
