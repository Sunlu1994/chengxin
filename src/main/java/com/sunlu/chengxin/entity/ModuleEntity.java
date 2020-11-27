package com.sunlu.chengxin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_module")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "module_name")
    private String moduleName;
    @Column(name = "module_icon")
    private String moduleIcon;

}
