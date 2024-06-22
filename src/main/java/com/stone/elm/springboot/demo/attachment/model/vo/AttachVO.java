package com.stone.elm.springboot.demo.attachment.model.vo;

import com.stone.elm.springboot.demo.attachment.model.root.AttachDtlRoot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "AttachVO", description = "附件信息实体VO")
public class AttachVO {
    @ApiModelProperty(value = "附件信息唯一标识")
    private Long attachID;

    @ApiModelProperty(value = "附件名称")
    private String attachName;

    @ApiModelProperty(value = "附件描述")
    private String attachDesc;

    @ApiModelProperty(value = "上传人员")
    private String uploadStf;

    @ApiModelProperty(value = "上传时间")
    private String uploadDate;

    @ApiModelProperty(value = "上传渠道")
    private String attachChannel;

    @ApiModelProperty(value = "上传渠道")
    private List<AttachDtlRoot> attachDtlList;

    public Long getAttachID() {
        return attachID;
    }

    public void setAttachID(Long attachID) {
        this.attachID = attachID;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachDesc() {
        return attachDesc;
    }

    public void setAttachDesc(String attachDesc) {
        this.attachDesc = attachDesc;
    }

    public String getUploadStf() {
        return uploadStf;
    }

    public void setUploadStf(String uploadStf) {
        this.uploadStf = uploadStf;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAttachChannel() {
        return attachChannel;
    }

    public void setAttachChannel(String attachChannel) {
        this.attachChannel = attachChannel;
    }

    public List<AttachDtlRoot> getAttachDtlList() {
        return attachDtlList;
    }

    public void setAttachDtlList(List<AttachDtlRoot> attachDtlList) {
        this.attachDtlList = attachDtlList;
    }
}
