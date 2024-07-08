package com.stone.elm.springboot.demo.attachment.model.ao;

import com.stone.elm.springboot.demo.basictech.common.requset.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AttachDtlAO extends QueryEntity {

    private Long attachDtlID;
    private Long attachID;
    private List<Long> attachDtlIDList;
    private List<Long> attachIDList;
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
    private MultipartFile multipartFile;
    private String isVideo;

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

    public List<Long> getAttachDtlIDList() {
        return attachDtlIDList;
    }

    public void setAttachDtlIDList(List<Long> attachDtlIDList) {
        this.attachDtlIDList = attachDtlIDList;
    }

    public List<Long> getAttachIDList() {
        return attachIDList;
    }

    public void setAttachIDList(List<Long> attachIDList) {
        this.attachIDList = attachIDList;
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

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(String isVideo) {
        this.isVideo = isVideo;
    }
}
