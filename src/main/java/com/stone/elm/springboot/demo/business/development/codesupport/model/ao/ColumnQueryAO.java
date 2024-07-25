package com.stone.elm.springboot.demo.business.development.codesupport.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ColumnQueryAO", description = "数据列查询AO")
public class ColumnQueryAO {

    @ApiModelProperty(value = "数据表名称")
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
