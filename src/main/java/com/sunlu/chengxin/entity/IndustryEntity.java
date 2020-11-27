package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_industry")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "行业列表")
public class IndustryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "行业编号")
    private Integer id;
    @Column(name = "industry_name")
    @ApiModelProperty(value = "行业名称")
    private String industryName;
    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 0删除 1未删除")
    private Integer isDelete;
}
