package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Integer> {
    List<AddressEntity> findByUserIdAndIsDelete(Integer userId,Integer isDelete);

    AddressEntity findByIdAndUserId(Integer id,Integer userId);
}
