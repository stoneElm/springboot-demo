package com.stone.elm.springboot.demo.business.development.codesupport.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ColumnQueryVO", description = "数据列查询VO")
public class ColumnQueryVO {
    @ApiModelProperty(value = "列名")
    private String columnName;

    @ApiModelProperty(value = "列描述")
    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
