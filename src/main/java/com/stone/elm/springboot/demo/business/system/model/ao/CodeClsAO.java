package com.stone.elm.springboot.demo.business.system.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "CodeClsAO", description = "标准代码分类实体AO")
public class CodeClsAO extends QueryEntity {
    public interface queryGroup{};
    public interface createGroup{};
    public interface updateGroup{};
    public interface deleteGroup{};

    @NotNull(message = "标准代码分类标识不能为空", groups = {updateGroup.class, deleteGroup.class})
    private Long codeClsID;

    private List<Long> codeClsIDList;

    private List<Long> notCodeClsIDList;

    @NotBlank(message = "标准代码分类名称不能为空", groups = {createGroup.class, updateGroup.class})
    private String codeClsName;

    @NotBlank(message = "标准代码分类类型不能为空", groups = {createGroup.class, updateGroup.class})
    private String codeClsType;

    private List<String> codeClsTypeList;

    private Long relaCodeClsID;

    @NotBlank(message = "生效时间不能为空", groups = {createGroup.class, updateGroup.class})
    private String validDate;

    @NotBlank(message = "生效时间不能为空", groups = {createGroup.class, updateGroup.class})
    private String invalidDate;

    @NotBlank(message = "生效标志不能为空", groups = {createGroup.class, updateGroup.class})
    private String validFlag;

    private String updateFlag;

    public Long getCodeClsID() {
        return codeClsID;
    }

    public void setCodeClsID(Long codeClsID) {
        this.codeClsID = codeClsID;
    }

    public List<Long> getCodeClsIDList() {
        return codeClsIDList;
    }

    public void setCodeClsIDList(List<Long> codeClsIDList) {
        this.codeClsIDList = codeClsIDList;
    }

    public List<Long> getNotCodeClsIDList() {
        return notCodeClsIDList;
    }

    public void setNotCodeClsIDList(List<Long> notCodeClsIDList) {
        this.notCodeClsIDList = notCodeClsIDList;
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

    public List<String> getCodeClsTypeList() {
        return codeClsTypeList;
    }

    public void setCodeClsTypeList(List<String> codeClsTypeList) {
        this.codeClsTypeList = codeClsTypeList;
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
        return "CodeClsAO{" +
                "codeClsID=" + codeClsID +
                ", codeClsIDList=" + codeClsIDList +
                ", notCodeClsIDList=" + notCodeClsIDList +
                ", codeClsName='" + codeClsName + '\'' +
                ", codeClsType='" + codeClsType + '\'' +
                ", codeClsTypeList=" + codeClsTypeList +
                ", relaCodeClsID=" + relaCodeClsID +
                ", validDate='" + validDate + '\'' +
                ", invalidDate='" + invalidDate + '\'' +
                ", validFlag='" + validFlag + '\'' +
                ", updateFlag='" + updateFlag + '\'' +
                '}';
    }
}
