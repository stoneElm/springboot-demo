package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "PersonalDocAO", description = "个人文档表查询AO")
public class PersonalDocAO extends QueryEntity {
    public interface selectGroup{}
    public interface createGroup{}
    public interface updateGroup{}
    public interface deleteGroup{}

    @NotNull(message = "个人文件唯一标识不能为空", groups = {updateGroup.class, deleteGroup.class})
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
}
