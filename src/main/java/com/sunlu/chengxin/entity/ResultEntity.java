package com.sunlu.chengxin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装返回结果
 * @param <T>
 */
//200200操作成功 200201操作失败 200204参数错误 200205操作异常
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回结果")
public class ResultEntity<T> implements Serializable {
    @ApiModelProperty(value = "操作码返回 200200操作成功 200201操作失败 200204参数错误 200205操作异常")
    private String code;
    @ApiModelProperty(value = "操作信息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;
}
