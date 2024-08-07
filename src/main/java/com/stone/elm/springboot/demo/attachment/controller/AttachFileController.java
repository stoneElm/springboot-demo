package com.stone.elm.springboot.demo.attachment.controller;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.root.AttachDtlRoot;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
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
    public ResponseResult<List<AttachDtlRoot>> batchUpload (AttachAO attachAO, @RequestParam("files") MultipartFile... files) {
        return iAttachFileService.batchUpload(attachAO, files);
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public void download (HttpServletRequest request, HttpServletResponse response,
                                              @RequestBody AttachDtlAO attachAO) {
        iAttachFileService.download(request, response, attachAO);
    }

    @GetMapping("/download/{attachDtlID}")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public void download (HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable Long attachDtlID) {
        iAttachFileService.download(request, response, attachDtlID);
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

    @PostMapping("/updateAttachDtlList")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseResult<List<AttachDtlVO>> updateAttachDtlList (@RequestBody List<AttachDtlAO> updateAttachDtlList) {
        return iAttachFileService.updateAttachDtlList(updateAttachDtlList);
    }

    @PostMapping("/deleteAttachDtlByID")
    @ApiOperation(value = "通过文件详情删除文件详情信息 维护人:Lan StoneElm")
    public ResponseResult<List<AttachDtlVO>> deleteAttachDtlByID (@RequestBody AttachDtlAO attachAO) {
        return iAttachFileService.deleteAttachDtlByID(attachAO);
    }

    @PostMapping("/deleteAttachDtlList")
    @ApiOperation(value = "通过文件详情删除文件详情信息 维护人:Lan StoneElm")
    public ResponseResult<List<AttachDtlVO>> deleteAttachDtlList (@RequestBody List<AttachDtlAO> deleteAttachDtlList) {
        return iAttachFileService.deleteAttachDtlList(deleteAttachDtlList, true);
    }

    @PostMapping("/getDownloadUrl")
    @ApiOperation(value = "文件下载 维护人:Lan StoneElm")
    public ResponseResult<AttachDtlVO> getDownloadUrl (@RequestBody AttachDtlAO attachAO) {
        return iAttachFileService.getDownloadUrl(attachAO);
    }
}
