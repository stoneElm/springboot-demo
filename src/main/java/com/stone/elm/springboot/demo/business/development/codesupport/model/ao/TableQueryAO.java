package com.stone.elm.springboot.demo.business.development.codesupport.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TableQueryAO", description = "数据表查询AO")
public class TableQueryAO {

    @ApiModelProperty(value = "数据库名称.表格模式")
    private String tableSchema;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
