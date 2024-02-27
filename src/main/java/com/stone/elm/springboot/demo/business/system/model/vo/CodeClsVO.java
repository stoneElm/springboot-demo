package com.stone.elm.springboot.demo.business.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CodeClsVO", description = "标准代码分类实体VO")
public class CodeClsVO {

    @ApiModelProperty(value = "标准代码分类唯一标识")
    private Long codeClsID;

    @ApiModelProperty(value = "标准代码分类名称")
    private String codeClsName;

    @ApiModelProperty(value = "标准代码分类类型")
    private String codeClsType;

    @ApiModelProperty(value = "关联标准代码分类唯一标识")
    private Long relaCodeClsID;

    @ApiModelProperty(value = "生效时间")
    private String validDate;

    @ApiModelProperty(value = "失效时间")
    private String invalidDate;

    @ApiModelProperty(value = "生效标志")
    private String validFlag;

    @ApiModelProperty(value = "是否可以被修改")
    private String updateFlag;

    public Long getCodeClsID() {
        return codeClsID;
    }

    public void setCodeClsID(Long codeClsID) {
        this.codeClsID = codeClsID;
    }

    public String getCodeClsName() {
        return codeClsName;
    }

    public void setCodeClsName(String codeClsName) {
        this.codeClsName = codeClsName;
    }

    public String getCodeClsType() {
        return codeClsType;
    }

    public void setCodeClsType(String codeClsType) {
        this.codeClsType = codeClsType;
    }

    public Long getRelaCodeClsID() {
        return relaCodeClsID;
    }

    public void setRelaCodeClsID(Long relaCodeClsID) {
        this.relaCodeClsID = relaCodeClsID;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    @Override
    public String toString() {
        return "CodeClsVO{" +
                "codeClsID=" + codeClsID +
                ", codeClsName='" + codeClsName + '\'' +
                ", codeClsType='" + codeClsType + '\'' +
                ", relaCodeClsID=" + relaCodeClsID +
                ", validDate='" + validDate + '\'' +
                ", invalidDate='" + invalidDate + '\'' +
                ", validFlag='" + validFlag + '\'' +
                ", updateFlag='" + updateFlag + '\'' +
                '}';
    }
}
