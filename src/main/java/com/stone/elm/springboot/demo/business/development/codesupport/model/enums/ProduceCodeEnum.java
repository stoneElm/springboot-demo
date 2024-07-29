package com.stone.elm.springboot.demo.business.development.codesupport.model.enums;

public enum ProduceCodeEnum {
    CLASS_AO("01", "生成AO类"),
    CLASS_VO("02", "生成VO类"),
    CONTROLLER("03", "生成Controller"),
    SERVICE("04", "生成Service"),
    SERVICE_IMPL("05", "生成ServiceImpl"),
    MAPPER("06", "生成Mapper"),
    MAPPER_XML("07", "生成MapperXML"),
    JSON_PARAM("08", "生成JSON入参");

    ProduceCodeEnum(String typeCode, String typeDesc) {
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
