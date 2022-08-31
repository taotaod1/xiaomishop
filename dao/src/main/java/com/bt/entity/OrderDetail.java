package com.bt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 10:20
 **/
@Data  //getter setter hashcode equail toString
@NoArgsConstructor  //无参构造方法
@AllArgsConstructor   //全参构造方法
public class OrderDetail {
    private Integer id;
    private String oid;
    private Integer pid;
    private Integer num;
    private BigDecimal money;
    private Goods goods;


}
