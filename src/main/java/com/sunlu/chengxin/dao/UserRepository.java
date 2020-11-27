package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.UserEntity;
import com.sunlu.chengxin.projection.UserInfoProjection;
import com.sunlu.chengxin.projection.UserLoginProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    //根据token 查找用户信息 state用户状态 0禁用 1启用  用户是否被删除 0删除 1未删除
    UserEntity findByToken(String token);

    //根据手机号查找用户信息
    UserEntity findByPhone(String phone);

    //登录验证接口 UserLoginEntity为spring data projection写法 jpa返回部分字段
    List<UserLoginProjection> findByNickNameAndPasswordAndIsDeleteAndState(String nickname, String password, Integer isDelete, Integer state);

    //忘记密码(根据手机号) 更新密码
    @Transactional
    @Query(value = "update t_user set password=:password where phone=:phone", nativeQuery = true)
    @Modifying(clearAutomatically = true) //需要更新缓存，否则更新后查询 数据库的字段可能修改了，但查出来的数据还是修改之前的
    void updateOne(@Param("password") String password,@Param("phone")String phone);

    //根据nickname 查找 生成token
    @Transactional
    @Query(value = "update t_user set token=:token where nickname=:nickname", nativeQuery = true)
    @Modifying(clearAutomatically = true) //需要更新缓存，否则更新后查询 数据库的字段可能修改了，但查出来的数据还是修改之前的
    void updateToken(@Param("nickname")String phone,@Param("token") String token);

    //根据 token 查看用户信息列表
    @Query(value = "select a.id,a.head_photo,a.nickname,a.sex,a.industry_id,b.industry_name,a.profession_id,c.profession_name,a.autograph from t_user a left join t_industry b on a.industry_id = b.id left join t_profession c on a.profession_id = c.id where token=:token",nativeQuery = true)
    List<UserInfoProjection> selectUserInfo(@Param("token")String token);

    //根据token修改用户信息
    @Transactional
    @Query(value = "update t_user set head_photo=:head_photo,nickname=:nickname,sex=:sex,industry_id=:industry_id,profession_id=:profession_id,autograph=:autograph where token=:token", nativeQuery = true)
    @Modifying(clearAutomatically = true) //需要更新缓存，否则更新后查询 数据库的字段可能修改了，但查出来的数据还是修改之前的
    void updateUserInfo(@Param("head_photo")String head_photo,@Param("nickname") String nickname,@Param("sex") String sex,@Param("industry_id") String industry_id,@Param("profession_id") String profession_id,@Param("autograph") String autograph,@Param("token") String token);

    //修改密码 根据用户token
    @Transactional
    @Query(value = "update t_user set password=:newPassword where token=:token", nativeQuery = true)
    @Modifying(clearAutomatically = true) //需要更新缓存，否则更新后查询 数据库的字段可能修改了，但查出来的数据还是修改之前的
    void updatePassword(@Param("token") String token,@Param("newPassword") String newPassword);

    //退出登录
    @Transactional
    @Query(value = "update t_user set token=\"\" where token=:token",nativeQuery = true)
    @Modifying(clearAutomatically = true) //需要更新缓存，否则更新后查询 数据库的字段可能修改了，但查出来的数据还是修改之前的
    void logout(@Param("token") String token);

}
