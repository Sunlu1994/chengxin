package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_classify")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "无限分类")
public class ClassifyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "类别名称")
    @Column(name = "classify_name")
    private String classifyName;
    @ApiModelProperty(value = "类别父类别id")
    @Column(name = "parent_classify_id")
    private Integer parentClassifyId;
    @ApiModelProperty(value = "是否删除 0删除 1未删除")
    @Column(name = "is_delete")
    private Integer isDelete;
}
