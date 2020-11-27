package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.IndustryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryRepository extends JpaRepository<IndustryEntity,Integer> {
    List<IndustryEntity> findByIsDelete(Integer isDelete);
}
