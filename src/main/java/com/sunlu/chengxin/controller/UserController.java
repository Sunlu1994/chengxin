package com.sunlu.chengxin.controller;
import com.sunlu.chengxin.aop.VisitPermission;
import com.sunlu.chengxin.entity.*;
import com.sunlu.chengxin.projection.UserInfoProjection;
import com.sunlu.chengxin.projection.UserLoginProjection;
import com.sunlu.chengxin.service.UserServiceImpl;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Jackson-databind是SpringBoot默认集成在web依赖中的框架，
 * 因此我们只需要引入spring-boot-starter-web依赖，返回实体类类型，就可以返回json数据
 */
@Api("用户登录相关接口")
@RestController
@RequestMapping("/chengxin/user")
public class UserController {
    @Resource
    UserServiceImpl userService;

    @ApiOperation(value = "用户名密码登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname",value = "用户名",defaultValue = ""),
            @ApiImplicitParam(name = "password",value = "密码",defaultValue = "")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultEntity<UserLoginProjection> login(String nickname, String password){
        return userService.login(nickname,password);
    }

    @ApiOperation("忘记密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",defaultValue = ""),
            @ApiImplicitParam(name = "password",value = "新密码",defaultValue = ""),
            @ApiImplicitParam(name = "repeatPassword",value = "重复密码",defaultValue = "")
    })
    @RequestMapping(value = "/forgetPassword",method = RequestMethod.POST)
    public ResultEntity forgetPassword(String phone, String password, String repeatPassword){
        return userService.forgetPassword(phone,password,repeatPassword);
    }

    @ApiOperation("用户信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = ""),
    })
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public ResultEntity<UserInfoProjection> getUserInfo(String token){
        //返回的头像 用ip:port+返回的headPhoto字段
        //如果存放图片的文件夹是项目打包后路径的static中，
        // 这样每次打包后，代码传到tomcat会覆盖掉上传图片的文件夹，导致之前文件被删掉 只能传新包的时候，先给图片拷贝出来一份
        //所以最好在tomcat中起一个专门存图片的文件夹，既可以保证访问域名+文件夹直接打开
        // 也不会因为打包覆盖掉这个文件夹
        return userService.userInfo(token);
    }

    @ApiOperation("更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = ""),
            @ApiImplicitParam(name = "headPhoto",value = "头像",defaultValue = ""),
            @ApiImplicitParam(name = "nickname",value = "昵称",defaultValue = ""),
            @ApiImplicitParam(name = "sex",value = "性别",defaultValue = ""),
            @ApiImplicitParam(name = "industryId",value = "行业id",defaultValue = ""),
            @ApiImplicitParam(name = "professionId",value = "职业id",defaultValue = ""),
            @ApiImplicitParam(name = "autograph",value = "签名",defaultValue = "")

    })
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public ResultEntity<UserInfoProjection> updateUserInfo(@RequestParam("headPhoto") MultipartFile headPhoto, String nickname, String sex, String industryId, String professionId, String autograph, String token){
        return userService.updateUserInfo(headPhoto,nickname,sex,industryId,professionId,autograph,token);
    }

    @ApiOperation("获取行业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/getIndustryList",method = RequestMethod.POST)
    public ResultEntity<IndustryEntity> getIndustryList(String token){
        return userService.getIndustryList(token);
    }

    @ApiOperation("获取职业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/getProfessionList",method = RequestMethod.POST)
    public ResultEntity<ProfessionEntity> getProfessionList(String token){
        return userService.getProfessionList(token);
    }

    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public ResultEntity getProfessionList(String token,String oldPassword,String newPassword){
        return userService.updatePassword(token,oldPassword,newPassword);
    }

    @ApiOperation("查询收货地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/getAddress",method = RequestMethod.POST)
    public ResultEntity<AddressEntity> getAddress(String token){
        return userService.getAddress(token);
    }

    @ApiOperation("添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = ""),
            @ApiImplicitParam(name = "recevier",value = "收件人",defaultValue = ""),
            @ApiImplicitParam(name = "receiverPhone",value = "收件人电话",defaultValue = ""),
            @ApiImplicitParam(name = "address",value = "收件地址",defaultValue = ""),
    })
    @RequestMapping(value = "/addAddress",method = RequestMethod.POST)
    public ResultEntity addAddress(String token,String recevier,String receiverPhone,String address){
        return userService.addAddress(token,recevier,receiverPhone,address);
    }

    @ApiOperation("修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = ""),
            @ApiImplicitParam(name = "addressId",value = "地址id",defaultValue = ""),
            @ApiImplicitParam(name = "recevier",value = "收件人",defaultValue = ""),
            @ApiImplicitParam(name = "receiverPhone",value = "收件人电话",defaultValue = ""),
            @ApiImplicitParam(name = "address",value = "收件地址",defaultValue = ""),
    })
    @RequestMapping(value = "/updateAddress",method = RequestMethod.POST)
    public ResultEntity updateAddress(String token,Integer addressId,String recevier,String receiverPhone,String address){
        return userService.updateAddress(token,addressId,recevier,receiverPhone,address);
    }

    @ApiOperation("退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "登录时返回的用户唯一码",defaultValue = "")
    })
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResultEntity logout(String token){
        return userService.logout(token);
    }


    /**
     * 配置权限注解 @VisitPermission("permission-test")
     * aop的使用测试
     * aop 环绕 发送日志到kafka 存入es
     */
    @VisitPermission("permission-test")
    @RequestMapping(value = "/testKafka",method = RequestMethod.GET)
    public String testKafka(String token111) {
        return "success";
    }
}
