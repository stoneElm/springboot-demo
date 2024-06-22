package com.stone.elm.springboot.demo.attachment.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AttachAO", description = "附件信息实体AO")
public class AttachAO {

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
    private String uploadChannel;

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

    public String getUploadChannel() {
        return uploadChannel;
    }

    public void setUploadChannel(String uploadChannel) {
        this.uploadChannel = uploadChannel;
    }
}
