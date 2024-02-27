package com.stone.elm.springboot.demo.business.system.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "CodeClsValAO", description = "标准代码值实体AO")
public class CodeClsValAO extends QueryEntity {
    public interface queryGroup{};
    public interface createGroup{};
    public interface updateGroup{};
    public interface deleteGroup{};

    @NotNull(message = "标准代码分类标识不能为空", groups = {deleteGroup.class, updateGroup.class})
    private Long codeClsValID;

    @NotBlank(message = "标准代码分类标识不能为空", groups = {createGroup.class, updateGroup.class})
    private String codeClsID;

    private List<Long> codeClsIDList;

    @NotBlank(message = "标准代码值不能为空", groups = {createGroup.class, updateGroup.class})
    private String codeClsVal;

    @NotBlank(message = "标准代码值名称不能为空", groups = {createGroup.class, updateGroup.class})
    private String codeClsValName;

    private String prntCodeClsVal;

    @NotBlank(message = "生效时间不能为空", groups = {createGroup.class, updateGroup.class})
    private String validDate;

    @NotBlank(message = "生效事件不能为空", groups = {createGroup.class, updateGroup.class})
    private String invalidDate;

    @NotBlank(message = "生效标志不能为空", groups = {createGroup.class, updateGroup.class})
    private String validFlag;

    private String serialNumber;

    private String codeClsCategName;

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

    public List<Long> getCodeClsIDList() {
        return codeClsIDList;
    }

    public void setCodeClsIDList(List<Long> codeClsIDList) {
        this.codeClsIDList = codeClsIDList;
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
        return "CodeClsValAO{" +
                "codeClsValID=" + codeClsValID +
                ", codeClsID='" + codeClsID + '\'' +
                ", codeClsIDList=" + codeClsIDList +
                ", codeClsVal='" + codeClsVal + '\'' +
                ", codeClsValName='" + codeClsValName + '\'' +
                ", prntCodeClsVal='" + prntCodeClsVal + '\'' +
                ", validDate='" + validDate + '\'' +
                ", invalidDate='" + invalidDate + '\'' +
                ", validFlag='" + validFlag + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", codeClsCategName='" + codeClsCategName + '\'' +
                ", codeValDesc='" + codeValDesc + '\'' +
                '}';
    }
}
