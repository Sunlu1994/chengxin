package com.sunlu.chengxin.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * Spring Data projection的使用
 * Projection并不是一个注解开关，而是一种写法
 * Spring Data允许自定义的返回类型，可以有选择地检索托需要的字段，避免查询所有字段。我们可以将返回类型定义为接口所需要的字段
 * 如果想把id改成userId返回，需要用到@Value填充值,同时给getId加上@JsonIgnore
 * 例如：
 *      @JsonIgnore
 *     Integer getId();
 *
 *     @Value("#{target.id}")
 *     String getUserid();
 */
@ApiModel(value = "登陆")
public interface UserLoginProjection {
//    @JsonIgnore
//    Integer getId();
//    @ApiModelProperty(value = "用户编号")
//    @Value("#{target.id}")
//    String getUserid();


    @ApiModelProperty(value = "用户唯一码，接口传参用来识别用户信息")
    String getToken();
}
