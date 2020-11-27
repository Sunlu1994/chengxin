package com.sunlu.chengxin.projection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(value = "用户信息")
public interface UserInfoProjection {
    @Value("#{target.nickname}")
    @ApiModelProperty(value = "用户名")
    String getNickname();
    @Value("#{target.head_photo}")
    @ApiModelProperty(value = "头像")
    String getHeadPhoto();
    @Value("#{target.sex}")
    @ApiModelProperty(value = "性别")
    String getSex();
    @Value("#{target.industry_id}")
    @ApiModelProperty(value = "行业编号")
    String getIndustryId();
    @Value("#{target.industry_name}")
    @ApiModelProperty(value = "行业名称")
    String getIndustryName();
    @Value("#{target.profession_id}")
    @ApiModelProperty(value = "职业编号")
    String getProfessionId();
    @Value("#{target.profession_name}")
    @ApiModelProperty(value = "职业名称")
    String getProfessionName();
    @Value("#{target.autograph}")
    @ApiModelProperty(value = "签名")
    String getAutograph();
}
