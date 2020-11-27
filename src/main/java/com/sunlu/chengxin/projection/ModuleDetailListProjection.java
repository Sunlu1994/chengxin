package com.sunlu.chengxin.projection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@ApiModel(value = "商品列表")
public interface ModuleDetailListProjection {
    @Value("#{target.goods_name}")
    @ApiModelProperty(value = "商品名")
    String getGoodsName();
    @Value("#{target.stock}")
    @ApiModelProperty(value = "库存数量")
    String getStock();
    @Value("#{target.goods_tag}")
    @ApiModelProperty(value = "商品标签")
    String getGoodsTag();
    @Value("#{target.shop_id}")
    @ApiModelProperty(value = "店铺id")
    Integer getShopId();
    @Value("#{target.goods_price}")
    @ApiModelProperty(value = "商品价格")
    BigDecimal getGoodsPrice();
}
