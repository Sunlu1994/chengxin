package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "t_user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户唯一码，接口传参用来识别用户信息")
    private Integer id;
    @Column(name = "nickname")
    @ApiModelProperty(value = "用户名")
    private String nickName;
    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;
    @Column(name = "head_photo")
    @ApiModelProperty(value = "头像:域名为：localhost:8080")
    private String headPhoto;
    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private String sex;
    @Column(name = "industry_id")
    @ApiModelProperty(value = "行业编号")
    private String industryId;
    @Column(name = "profession_id")
    @ApiModelProperty(value = "职业编号")
    private String professionId;
    @Column(name = "autograph")
    @ApiModelProperty(value = "用户签名")
    private String autograph;//签名
    @Column(name = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;//签名
    @Column(name = "token")
    @ApiModelProperty(value = "用户token")
    private String token;//签名
    @Column(name = "state")//0禁用 1启用
    @ApiModelProperty(value = "用户是否被禁用")
    private Integer state;
    @Column(name = "is_delete")
    private Integer isDelete;
}
