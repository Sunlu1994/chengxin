package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.ClassifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyRepository extends JpaRepository<ClassifyEntity,Integer> {
}
