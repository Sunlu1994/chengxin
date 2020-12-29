package com.sunlu.chengxin.controller;

import com.sunlu.chengxin.dao.GoodsRepository;
import com.sunlu.chengxin.entity.GoodsEntity;
import com.sunlu.chengxin.entity.ResultEntity;
import com.sunlu.chengxin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("首页相关接口")
@RestController
@RequestMapping("/chengxin/index")
public class IndexController {
    @Autowired
    IndexService indexService;
    @ApiOperation(value = "获取功能列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/getModuleList",method = RequestMethod.GET)
    public ResultEntity getModuleList(String token){
        return indexService.getModuleList(token);
    }

    @ApiOperation(value = "获取功能列表下的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue ="" ),
            @ApiImplicitParam(name = "module_id",value = "模块id",defaultValue ="1" )
    })
    @RequestMapping(value = "/getModuleDetailList",method = RequestMethod.GET)
    public ResultEntity getModuleDetailList(String token,@RequestParam("module_id") Integer moduleId){
        return indexService.getModuleDetailList(token,moduleId);
    }

    @ApiOperation(value = "获取商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue ="" ),
            @ApiImplicitParam(name = "goods_id",value = "商品id",defaultValue ="1" )
    })
    @RequestMapping(value = "/getGoodsDetail",method = RequestMethod.GET)
    public ResultEntity getGoodsDetail(String token,@RequestParam("goods_id") Integer goodsId){
        return indexService.getGoodsDetail(token,goodsId);
    }

    @ApiOperation(value = "获取筛选分类列表(无限分类)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue ="" )
    })
    @PostMapping("/getClassifyList")
    public ResultEntity getClassifyList(String token){
        return indexService.getClassifyList(token);
    }

    @ApiOperation(value = "获取商品评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = ""),
            @ApiImplicitParam(name = "goodId",value = "商品id",defaultValue = "")
    })
    @PostMapping("/getGoodComment")
    public ResultEntity getGoodComment(String token,Integer goodId){
        return indexService.getGoodComment(token,goodId);
    }


    @PostMapping("/liufei")
    public String liufei(String t, String token, HttpServletResponse response){
        System.out.println(t);
        return "t";
    }
}
