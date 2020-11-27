package com.sunlu.chengxin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "t_goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "goods_name")
    private String goodsName;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "goods_price")
    private BigDecimal goodsPrice;
    @Column(name = "goods_introduction")
    private String goodsIntroduction;
    @Column(name = "goods_tag")
    private String goodsTag;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "goods_details")
    private String goodsDetails;
    @Column(name = "goods_pic_display")
    private String goodsPic_display;
    @Column(name = "goods_module_id")
    private Integer goodsModuleId;
    @Column(name = "accessories_introduction")
    private String accessoriesIntroduction;
    @Column(name = "parchase_instructions")
    private String parchaseInstructions;
}
