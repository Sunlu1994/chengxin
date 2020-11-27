package com.sunlu.chengxin.service;

import com.sunlu.chengxin.entity.ResultEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    //登录
    ResultEntity<List> login(String nickname, String password);
    //忘记密码(根据手机号修改)
    ResultEntity<List> forgetPassword(String password,String repeatPassword,String phone);
    //查询用户信息
    ResultEntity<List> userInfo(String token);

    //修改用户信息
    ResultEntity<List> updateUserInfo(MultipartFile headPhoto, String nickname, String sex, String industryId, String professionId, String autograph, String token);

    //获取行业列表
    ResultEntity<List> getIndustryList(String token);

    //获取职业列表
    ResultEntity<List> getProfessionList(String token);

    //修改密码 根据token
    ResultEntity<List> updatePassword(String token,String oldPassword,String newPassword);

    //添加收货地址
    ResultEntity<List> addAddress(String token,String recevier,String receiverPhone,String address);

    //删除地址
    ResultEntity<List> deleteAddress(String token,Integer addressId);

    //修改地址
    ResultEntity<List> updateAddress(String token,Integer addressId, String recevier, String receiverPhone, String address);

    //查询收货地址
    ResultEntity<List> getAddress(String token);

    //退出登录
    ResultEntity<List> logout(String token);
}
