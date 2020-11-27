package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.GoodsEntity;
import com.sunlu.chengxin.projection.ModuleDetailListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<GoodsEntity,Integer> {
    //根据模块分类查询商品列表
    @Query(value = "SELECT goods_name,goods_price,stock,shop_id,goods_tag FROM t_goods where goods_module_id = :goodsModuleId",nativeQuery = true)
    List<ModuleDetailListProjection> findByGoodsModuleId(@Param("goodsModuleId") Integer goodsModuleId);

}
