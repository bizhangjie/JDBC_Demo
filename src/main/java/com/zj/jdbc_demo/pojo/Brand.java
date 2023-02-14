package com.zj.jdbc_demo.pojo;

import lombok.Data;

/**
 * @Author 章杰
 * @Date 2023/2/8 13:07
 * @Version 1.0.0
 */
@Data
public class Brand {

    // id 主键
    private Integer id;
    // 品牌名称
    private String brandName;
    // 企业名称
    private String companyName;
    // 排序字段
    private Integer ordered;
    // 描述信息
    private String description;
    // 状态：0：禁用 1：启用
    private Integer status;
}
