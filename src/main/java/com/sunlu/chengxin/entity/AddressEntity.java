package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_address")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "收货地址")
public class AddressEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "地址编号")
    private Integer id;
    @Column(name = "addr")
    @ApiModelProperty(value = "地址")
    private String addr;
    @Column(name = "receiver")
    @ApiModelProperty(value = "收件人")
    private String receiver;
    @Column(name = "receiver_phone")
    @ApiModelProperty(value = "收件人电话")
    private String recevierPhone;
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户编号")
    private Integer userId;
    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 0删除 1未删除")
    private Integer isDelete;

}
