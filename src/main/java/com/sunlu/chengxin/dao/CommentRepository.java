package com.sunlu.chengxin.dao;

import com.sunlu.chengxin.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Integer> {
    List<CommentEntity> findByGoodIdOrderByAddTime(Integer goodId);
}
