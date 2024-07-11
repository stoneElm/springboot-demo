package com.stone.elm.springboot.demo.attachment.controller;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachVO;
import com.stone.elm.springboot.demo.attachment.service.IAttachFileService;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "附件中心/文件服务")
@RestController("attachmentFileServiceController")
@RequestMapping("/attachment/files")
public class AttachFileController {

    @Autowired
    private IAttachFileService iAttachFileService;

    @PostMapping("/batchUpload")
    @ApiOperation(value = "批量文件上传 维护人:Lan StoneElm")
    public ResponseResult<List<AttachVO>> batchUpload (AttachAO attachAO, @RequestParam("files") MultipartFile... files) {
        return iAttachFileService.batchUpload(attachAO, files);
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseEntity<Resource> download (HttpServletRequest request, HttpServletResponse response,
                                              @RequestBody AttachDtlAO attachAO) {
        return iAttachFileService.download(request, response, attachAO, false);
    }

    @GetMapping("/download/{attachDtlID}")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseEntity<Resource> download (@PathVariable Long attachDtlID) {
        return iAttachFileService.download(attachDtlID);
    }

    @GetMapping("/filePreview")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseEntity<Resource> filePreview (@RequestParam("attachDtlID") Long attachDtlID) {
        return iAttachFileService.filePreview(attachDtlID);
    }

    @PostMapping("/selectAttachDtlList")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseResult<List<AttachDtlVO>> selectAttachDtlList (@RequestBody AttachDtlAO attachAO) {
        return iAttachFileService.selectAttachDtlList(attachAO);
    }

    @PostMapping("/getDownloadUrl")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseResult<AttachDtlVO> getDownloadUrl (@RequestBody AttachDtlAO attachAO) {
        return iAttachFileService.getDownloadUrl(attachAO);
    }
}
