package com.stone.elm.springboot.demo.business.development.codesupport.model.ao;

import com.stone.elm.springboot.demo.business.system.model.ao.CodeClsAO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "ColumnQueryAO", description = "数据列查询AO")
public class ColumnQueryAO {
    public interface queryGroup{}

    @NotBlank(message = "数据表名称不能为空", groups = {ColumnQueryAO.queryGroup.class})
    @ApiModelProperty(value = "数据表名称")
    private String tableName;

    @NotBlank(message = "操作类型不能为空", groups = {ColumnQueryAO.queryGroup.class})
    @ApiModelProperty(value = "操作类型")
    private String operationType;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
