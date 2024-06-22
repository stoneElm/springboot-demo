package com.stone.elm.springboot.demo.attachment.model.vo;

import org.springframework.web.multipart.MultipartFile;

public class AttachDtlVO {

    private Long attachDtlID;
    private Long attachID;
    private String attachDtlName;
    private String attachDtlDesc;
    private String attachDtlPath;
    private String attachDtlFmt;
    private String attachDtlStat;
    private String attachDtlSize;
    private String attachDtlCateg;
    private String attachDtlType;
    private String attachDtlContentType;
    private String uploadStf;
    private String uploadDate;

    public Long getAttachDtlID() {
        return attachDtlID;
    }

    public void setAttachDtlID(Long attachDtlID) {
        this.attachDtlID = attachDtlID;
    }

    public Long getAttachID() {
        return attachID;
    }

    public void setAttachID(Long attachID) {
        this.attachID = attachID;
    }

    public String getAttachDtlName() {
        return attachDtlName;
    }

    public void setAttachDtlName(String attachDtlName) {
        this.attachDtlName = attachDtlName;
    }

    public String getAttachDtlDesc() {
        return attachDtlDesc;
    }

    public void setAttachDtlDesc(String attachDtlDesc) {
        this.attachDtlDesc = attachDtlDesc;
    }

    public String getAttachDtlPath() {
        return attachDtlPath;
    }

    public void setAttachDtlPath(String attachDtlPath) {
        this.attachDtlPath = attachDtlPath;
    }

    public String getAttachDtlFmt() {
        return attachDtlFmt;
    }

    public void setAttachDtlFmt(String attachDtlFmt) {
        this.attachDtlFmt = attachDtlFmt;
    }

    public String getAttachDtlStat() {
        return attachDtlStat;
    }

    public void setAttachDtlStat(String attachDtlStat) {
        this.attachDtlStat = attachDtlStat;
    }

    public String getAttachDtlSize() {
        return attachDtlSize;
    }

    public void setAttachDtlSize(String attachDtlSize) {
        this.attachDtlSize = attachDtlSize;
    }

    public String getAttachDtlCateg() {
        return attachDtlCateg;
    }

    public void setAttachDtlCateg(String attachDtlCateg) {
        this.attachDtlCateg = attachDtlCateg;
    }

    public String getAttachDtlType() {
        return attachDtlType;
    }

    public void setAttachDtlType(String attachDtlType) {
        this.attachDtlType = attachDtlType;
    }

    public String getAttachDtlContentType() {
        return attachDtlContentType;
    }

    public void setAttachDtlContentType(String attachDtlContentType) {
        this.attachDtlContentType = attachDtlContentType;
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
}
