package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_profession")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "职业列表")
public class ProfessionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "职业编号")
    private Integer id;
    @Column(name = "profession_name")
    @ApiModelProperty(value = "职业名称")
    private String professionName;
    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 0删除 1未删除")
    private Integer isDelete;
}
