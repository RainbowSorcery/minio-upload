package com.tracetech.minioserver.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationFileObject {
    @ApiModelProperty(value = "bucket名称", position = 1)
    private String bucketName;
    @ApiModelProperty(value = "对象名称", position = 1)
    private String objectName;
}
