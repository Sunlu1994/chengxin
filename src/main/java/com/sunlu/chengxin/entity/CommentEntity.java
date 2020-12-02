package com.sunlu.chengxin.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "t_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品评价")
public class CommentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "grade")
    private Float grade;
    @Column(name = "add_time")
    private Timestamp addTime;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String username;
    @Column(name = "good_id")
    private Integer goodId;
}
