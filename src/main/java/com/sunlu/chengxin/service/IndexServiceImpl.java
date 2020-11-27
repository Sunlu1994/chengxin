package com.sunlu.chengxin.service;

import com.alibaba.fastjson.JSON;
import com.sunlu.chengxin.common.Utils;
import com.sunlu.chengxin.dao.ClassifyRepository;
import com.sunlu.chengxin.dao.GoodsRepository;
import com.sunlu.chengxin.dao.ModuleRepository;
import com.sunlu.chengxin.entity.ClassifyEntity;
import com.sunlu.chengxin.projection.ModuleDetailListProjection;
import com.sunlu.chengxin.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService{
    @Autowired
    ModuleRepository moduleRepository;
    /**
     * 获取模块列表
     * @return
     */
    @Override
    public ResultEntity<List> getModuleList(String token) {
        return Utils.createJson("200200","获取模块列表成功",moduleRepository.findAll());
    }

    @Autowired
    GoodsRepository goodsRepository;
    @Override
    public ResultEntity<List> getModuleDetailList(String token, Integer moduleId) {
        List<Object> list = new ArrayList();
        for (ModuleDetailListProjection moduleDetailListProjection : goodsRepository.findByGoodsModuleId(moduleId)) {
            System.out.println(moduleDetailListProjection);
            System.out.println(JSON.toJSONString(moduleDetailListProjection));
            //逗号分隔商品标签
            Map map = JSON.parseObject(JSON.toJSONString(moduleDetailListProjection), Map.class);
            String[] str = String.valueOf(map.get("goodsTag")).split(",");
            List<Map<String,String>> listTemp = new ArrayList<>();
            for (String s: str) {
                Map<String,String> mapTemp = new HashMap<>();
                mapTemp.put("tagName",s);
                listTemp.add(mapTemp);
            }
            map.put("goodsTag",listTemp);
            //因为ModuleDetailListEntity不是一个真正意义的实体类对象，只是一个临时对象，所以转成string会多出两个键
            map.remove("targetClass");
            map.remove("target");
            list.add(map);
        }

        return Utils.createJson("200200","获取商品列表成功",list);
    }

    @Override
    public ResultEntity<List> getGoodsDetail(String token, Integer goodsId) {
        List<Object> list = new ArrayList();
        //逗号分隔商品标签
        Map map = JSON.parseObject(JSON.toJSONString(goodsRepository.findById(goodsId).get()), Map.class);
        String[] str = String.valueOf(map.get("goodsTag")).split(",");
        List<Map<String,String>> listTemp = new ArrayList<>();
        for (String s: str) {
            Map<String,String> mapTemp = new HashMap<>();
            mapTemp.put("tagName",s);
            listTemp.add(mapTemp);
        }
        map.put("goodsTag",listTemp);


        //整机模块，需要返回关联配件 id=1 移除配件介绍和购买须知
        if (Integer.valueOf(map.get("goodsModuleId").toString())==1){
            map.remove("accessoriesIntroduction");
            map.remove("parchaseInstructions");
        }
        //配件模块，需要返回配件介绍等 id=2 直接返回实体类就行
//        else if(Integer.valueOf(map.get("goodsModuleId").toString())==2){
//
//        }
        list.add(map);


        return Utils.createJson("200200","获取商品详情成功",map);
    }

    //获取筛选分类(无限分类)
    @Autowired
    ClassifyRepository classifyRepository;
    @Override
    public ResultEntity<List> getClassifyList(String token) {
        List<Map> list = new ArrayList<>();
        tree(classifyRepository.findAll(),0,list);
        return Utils.createJson("200200","获取成功",list);
    }


    //封装的递归无限分类结果查询 cs所有数据，pid是父级分类id，list是要往哪个list中添加数据
    public static void tree(List<ClassifyEntity> cs , Integer pid , List<Map> list){
        for (ClassifyEntity c: cs) {
            if( c.getParentClassifyId() == pid ){
                //封装返回结果
                Map m = treeData(c);
                list.add(m);
                tree( cs , c.getId(),((List)m.get("list")));
            }
        }
    }
    //返回数据的结构
    public static Map<String,Object> treeData(ClassifyEntity cs){
        List<Map<String,Object>> listTemp = new ArrayList<>();
        Map<String,Object> mapTemp = new HashMap<>();
        mapTemp.put("id",cs.getId());
        mapTemp.put("parentId",cs.getParentClassifyId());
        mapTemp.put("name",cs.getClassifyName());
        mapTemp.put("list",listTemp);
        return mapTemp;
    }
}
