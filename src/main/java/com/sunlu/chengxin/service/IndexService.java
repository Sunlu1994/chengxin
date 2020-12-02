package com.sunlu.chengxin.service;

import com.sunlu.chengxin.entity.ResultEntity;

import java.util.List;


public interface IndexService {
    //获取模块列表
    ResultEntity<List> getModuleList(String token);

    //获取对应模块下的商品列表
    ResultEntity<List> getModuleDetailList(String token,Integer moduleId);

    //查找商品详情
    ResultEntity<List> getGoodsDetail(String token, Integer goodsId);

    //获取类别
    ResultEntity<List> getClassifyList(String token);

    //获取商品评价
   ResultEntity<List> getGoodComment(String token,Integer goodId);

}
