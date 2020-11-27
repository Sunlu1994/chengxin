package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.IndustryEntity;
import com.sunlu.chengxin.entity.ProfessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfressionRepository extends JpaRepository<ProfessionEntity,Integer> {
    List<ProfessionEntity> findByIsDelete(Integer isDelete);
}
