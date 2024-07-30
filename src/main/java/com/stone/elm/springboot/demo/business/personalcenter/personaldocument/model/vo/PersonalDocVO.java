package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PersonalDocVO", description = "个人文档表查询VO")
public class PersonalDocVO {

    @ApiModelProperty(value = "个人文件唯一标识")
    private Long personalDocID;

    @ApiModelProperty(value = "文档名称")
    private String personalDocName;

    @ApiModelProperty(value = "文档类型")
    private String personalDocType;

    @ApiModelProperty(value = "创建者")
    private String createStf;

    @ApiModelProperty(value = "权限")
    private String limitsAuthority;

    @ApiModelProperty(value = "父级目录唯一标识")
    private Long parentDocID;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "更新时间")
    private String updateDate;

    @ApiModelProperty(value = "关联附件详情唯一标识")
    private Long attachDtlID;

    @ApiModelProperty(value = "附件名称")
    private String attachDtlName;

    public Long getPersonalDocID() {
        return personalDocID;
    }

    public void setPersonalDocID(Long personalDocID) {
        this.personalDocID = personalDocID;
    }

    public String getPersonalDocName() {
        return personalDocName;
    }

    public void setPersonalDocName(String personalDocName) {
        this.personalDocName = personalDocName;
    }

    public String getPersonalDocType() {
        return personalDocType;
    }

    public void setPersonalDocType(String personalDocType) {
        this.personalDocType = personalDocType;
    }

    public String getCreateStf() {
        return createStf;
    }

    public void setCreateStf(String createStf) {
        this.createStf = createStf;
    }

    public String getLimitsAuthority() {
        return limitsAuthority;
    }

    public void setLimitsAuthority(String limitsAuthority) {
        this.limitsAuthority = limitsAuthority;
    }

    public Long getParentDocID() {
        return parentDocID;
    }

    public void setParentDocID(Long parentDocID) {
        this.parentDocID = parentDocID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long getAttachDtlID() {
        return attachDtlID;
    }

    public void setAttachDtlID(Long attachDtlID) {
        this.attachDtlID = attachDtlID;
    }

    public String getAttachDtlName() {
        return attachDtlName;
    }

    public void setAttachDtlName(String attachDtlName) {
        this.attachDtlName = attachDtlName;
    }
}
