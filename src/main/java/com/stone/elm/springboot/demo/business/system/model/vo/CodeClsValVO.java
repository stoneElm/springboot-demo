package com.stone.elm.springboot.demo.business.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CodeClsValVO", description = "标准代码值实体VO")
public class CodeClsValVO {

    @ApiModelProperty(value = "标准代码值唯一标识")
    private Long codeClsValID;

    @ApiModelProperty(value = "关联标准代码分类唯一标识")
    private String codeClsID;

    @ApiModelProperty(value = "标准代码值")
    private String codeClsVal;

    @ApiModelProperty(value = "标准代码值名称")
    private String codeClsValName;

    @ApiModelProperty(value = "关联标准代码值")
    private String prntCodeClsVal;

    @ApiModelProperty(value = "生效时间")
    private String validDate;

    @ApiModelProperty(value = "失效时间")
    private String invalidDate;

    @ApiModelProperty(value = "生效标志")
    private String validFlag;

    @ApiModelProperty(value = "排序")
    private String serialNumber;

    @ApiModelProperty(value = "代码分类的类别名称")
    private String codeClsCategName;

    @ApiModelProperty(value = "描述")
    private String codeValDesc;

    public Long getCodeClsValID() {
        return codeClsValID;
    }

    public void setCodeClsValID(Long codeClsValID) {
        this.codeClsValID = codeClsValID;
    }

    public String getCodeClsID() {
        return codeClsID;
    }

    public void setCodeClsID(String codeClsID) {
        this.codeClsID = codeClsID;
    }

    public String getCodeClsVal() {
        return codeClsVal;
    }

    public void setCodeClsVal(String codeClsVal) {
        this.codeClsVal = codeClsVal;
    }

    public String getCodeClsValName() {
        return codeClsValName;
    }

    public void setCodeClsValName(String codeClsValName) {
        this.codeClsValName = codeClsValName;
    }

    public String getPrntCodeClsVal() {
        return prntCodeClsVal;
    }

    public void setPrntCodeClsVal(String prntCodeClsVal) {
        this.prntCodeClsVal = prntCodeClsVal;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCodeClsCategName() {
        return codeClsCategName;
    }

    public void setCodeClsCategName(String codeClsCategName) {
        this.codeClsCategName = codeClsCategName;
    }

    public String getCodeValDesc() {
        return codeValDesc;
    }

    public void setCodeValDesc(String codeValDesc) {
        this.codeValDesc = codeValDesc;
    }

    @Override
    public String toString() {
        return "CodeClsValVO{" +
                "codeClsValID='" + codeClsValID + '\'' +
                ", codeClsID='" + codeClsID + '\'' +
                ", codeClsVal='" + codeClsVal + '\'' +
                ", codeClsValName='" + codeClsValName + '\'' +
                ", prntCodeClsVal='" + prntCodeClsVal + '\'' +
                ", validDate=" + validDate +
                ", invalidDate=" + invalidDate +
                ", validFlag='" + validFlag + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", codeClsCategName='" + codeClsCategName + '\'' +
                ", codeValDesc='" + codeValDesc + '\'' +
                '}';
    }
}
