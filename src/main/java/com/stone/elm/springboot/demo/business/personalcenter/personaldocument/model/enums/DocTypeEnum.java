package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.enums;

public enum DocTypeEnum {
    FOLDER("01", "目录"),
    FILE("02", "文件");

    DocTypeEnum(String typeCode, String typeDesc) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;
    }

    private final String typeCode;
    private final String typeDesc;

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
