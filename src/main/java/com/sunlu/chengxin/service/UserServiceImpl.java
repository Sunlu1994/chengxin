package com.sunlu.chengxin.service;

import com.sunlu.chengxin.common.Utils;
import com.sunlu.chengxin.dao.AddressRepository;
import com.sunlu.chengxin.dao.IndustryRepository;
import com.sunlu.chengxin.dao.ProfressionRepository;
import com.sunlu.chengxin.dao.UserRepository;
import com.sunlu.chengxin.entity.*;
import com.sunlu.chengxin.projection.UserInfoProjection;
import com.sunlu.chengxin.projection.UserLoginProjection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 用户管理相关操作
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserRepository userRepository;

    @Resource
    IndustryRepository industryRepository;

    @Resource
    ProfressionRepository profressionRepository;

    @Resource
    AddressRepository addressRepository;
    /**
     *登录接口
     */

    @Override
    public ResultEntity login(String nickname, String password) {
        if (Utils.isEmpty(nickname) || Utils.isEmpty(password)){
            return Utils.createJson("200204","200204,参数错误，用户名密码不能为空",new ArrayList<>());
        }
        List<UserLoginProjection> list = userRepository.findByNickNameAndPasswordAndIsDeleteAndState(nickname,password,1,1);
        if (Utils.isNotEmpty(list)){
            //登陆成功 更新token
            //生成token方式为：uuid
            userRepository.updateToken(nickname,UUID.randomUUID().toString().replaceAll("-",""));

            return Utils.createJson("200200","用户名密码正确",userRepository.findByNickNameAndPasswordAndIsDeleteAndState(nickname,password,1,1));
        }else{
            return Utils.createJson("200204","参数错误，用户名密码错误，或用户已删除或用户未启用",list);
        }
    }

    //忘记密码
    //修改要事务操作，注意表要是InnoDB类型
    @Override
    public ResultEntity forgetPassword(String phone,String password,String repeatPassword){
        if (Utils.isEmpty(phone) || Utils.isEmpty(password) || Utils.isEmpty(repeatPassword)){
            return Utils.createJson("200204","参数错误",new ArrayList<>());
        }

        if (!(password.equals(repeatPassword))){
            return Utils.createJson("200204","参数错误,两次密码不一致",new ArrayList<>());
        }
        UserEntity userEntity = userRepository.findByPhone(phone);
        if (Utils.isEmpty(userEntity)){
            return Utils.createJson("200204","无效的手机号",new ArrayList<>());
        }else{
            if (userEntity.getIsDelete()==1 && userEntity.getState() == 1){
                try{
                    userRepository.updateOne(password,phone);
                    return Utils.createJson("200200","修改成功",new ArrayList<>());
                }catch (Exception e){
                    e.printStackTrace();
                    return Utils.createJson("200205","修改数据异常",new ArrayList<>());
                }
            }else{
                return Utils.createJson("200205","用户未启用或已删除",new ArrayList<>());
            }
        }
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public ResultEntity userInfo(String token) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的 并且用户不能为is_detete=0 state=0
        List<UserInfoProjection> list = userRepository.selectUserInfo(token);
        if (Utils.isEmpty(list)){
            return Utils.createJson("200204","参数错误，无效的token",list);
        }else{
            return Utils.createJson("200200","成功",list);
        }
    }

    /**
     * 更新用户信息
     * @param headPhoto
     * @param nickname
     * @param sex
     * @param industryId
     * @param professionId
     * @param autograph
     * @param token
     * @return
     */

    @Override
    public ResultEntity updateUserInfo(MultipartFile headPhoto, String nickname, String sex, String industryId, String professionId, String autograph, String token) {
        if (headPhoto.isEmpty()){
            return Utils.createJson("200204","参数错误",new ArrayList<>());
        }
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        UserEntity userEntity = userRepository.findByToken(token);
        String fileName = headPhoto.getOriginalFilename();
        //放在了tomcat中，能直接通过ip:port/uploadfile/文件名访问资源，库中只保留/uploadfile/文件名
        //
        String filePath = "C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/uploadfile/";
        try {
            filePath = java.net.URLDecoder.decode(filePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(filePath);
        System.out.println(fileName);
        File dest = new File(filePath + fileName);
        try {
            headPhoto.transferTo(dest);
            userRepository.updateUserInfo("/uploadfile/"+fileName,nickname,sex,industryId,professionId,autograph,token);
            return Utils.createJson("200200","成功",userRepository.selectUserInfo(token));
        } catch (IOException e) {
            e.printStackTrace();
            return Utils.createJson("200205","上传头像异常",new ArrayList<>());
        }
    }

    /**
     * 获取行业列表
     * @param token
     * @return
     */
    @Override
    public ResultEntity getIndustryList(String token) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        UserEntity userEntity = userRepository.findByToken(token);
        return Utils.createJson("200200","成功",industryRepository.findByIsDelete(1));
    }

    /**
     * 获取职业列表
     * @param token
     * @return
     */
    @Override
    public ResultEntity getProfessionList(String token) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        return Utils.createJson("200200","成功",profressionRepository.findByIsDelete(1));
    }

    //修改密码
    @Override
    public ResultEntity updatePassword(String token, String oldPassword, String newPassword) {
        if (Utils.isEmpty(oldPassword) || Utils.isEmpty(newPassword)){
            return Utils.createJson("200204","参数错误",new ArrayList<>());
        }
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        UserEntity userEntity = userRepository.findByToken(token);
        if (oldPassword.equals(userEntity.getPassword())){
            userRepository.updatePassword(token,newPassword);
            return Utils.createJson("200200","成功",new ArrayList<>());
        }else {
            return Utils.createJson("200204", "原密码输入有误", new ArrayList<>());
        }
    }

    /**
     * 添加地址
     * @param token
     * @param recevier
     * @param receiverPhone
     * @param address
     * @return
     */
    @Override
    public ResultEntity addAddress(String token, String recevier, String receiverPhone, String address) {
        if (Utils.isEmpty(recevier) || Utils.isEmpty(receiverPhone) || Utils.isEmpty(address)){
            return Utils.createJson("200204","参数错误",new ArrayList<>());
        }
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        UserEntity userEntity = userRepository.findByToken(token);
        AddressEntity addressEntity = addressRepository.save(new AddressEntity(null,address,recevier,receiverPhone,userEntity.getId(),1));
        Map<String,Integer> map = new HashMap<>();
        map.put("id",addressEntity.getId());
        return Utils.createJson("200200","成功",map);
    }

    /**
     * 删除地址
     * @param token
     * @return
     */
    @Override
    public ResultEntity deleteAddress(String token,Integer addressId) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        UserEntity userEntity = userRepository.findByToken(token);
        AddressEntity addressEntity = addressRepository.findByIdAndUserId(addressId,userEntity.getId());
        if (addressEntity!=null){
            //修改地址
            addressRepository.save(new AddressEntity(addressId,addressEntity.getAddr(),addressEntity.getReceiver(),addressEntity.getRecevierPhone(),userEntity.getId(),0));
            return Utils.createJson("200200","删除成功" ,new ArrayList<>());
        }else{

            //根据用户id查询地址
            return Utils.createJson("200204","操作失败，地址id错误" ,new ArrayList<>());
        }
    }

    /**
     * 修改地址
     * @param token
     * @param recevier
     * @param receiverPhone
     * @param address
     * @return
     */
    @Override
    public ResultEntity updateAddress(String token,Integer addressId, String recevier, String receiverPhone, String address) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token,所以这里认为token都是有效的
        if (Utils.isEmpty(recevier) || Utils.isEmpty(receiverPhone) || Utils.isEmpty(address)){
            return Utils.createJson("200204","参数错误",new ArrayList<>());
        }

        UserEntity userEntity = userRepository.findByToken(token);
        AddressEntity addressEntity = addressRepository.findByIdAndUserId(addressId,userEntity.getId());
        if (addressEntity!=null){
            //修改地址
            addressRepository.save(new AddressEntity(addressId,address,recevier,receiverPhone,userEntity.getId(),1));
            return Utils.createJson("200200","修改成功" ,new ArrayList<>());
        }else{

            //根据用户id查询地址
            return Utils.createJson("200204","操作失败，地址id错误" ,new ArrayList<>());
        }
    }

    /**
     * 获取收货地址
     * @param token
     * @return
     */
    @Override
    public ResultEntity getAddress(String token) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token 所以这里认为拦截器方形的token都是有效token
        UserEntity userEntity = userRepository.findByToken(token);
        //根据用户id查询地址
        return Utils.createJson("200200","成功",addressRepository.findByUserIdAndIsDelete(userEntity.getId(),1));
    }

    /**
     * 注销登陆
     * @param token
     * @return
     */
    @Override
    public ResultEntity logout(String token) {
        //在项目拦截器中判断了token不能为空 ，并且不能为失效token ,所以这里认为token都是有效的
        userRepository.logout(token);
        //根据用户id查询地址
        return Utils.createJson("200200","成功",new ArrayList());
    }

}
