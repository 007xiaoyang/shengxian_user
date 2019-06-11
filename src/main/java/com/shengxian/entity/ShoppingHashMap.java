package com.shengxian.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-05-09
 * @Version: 1.0
 */
public class ShoppingHashMap {

    private Integer business_id;
    private Double starting_price;
    private String store_name;

    private Integer sc_id; // 购物车id
    private Integer count ;
    private List goodsDetail;
    private BigDecimal tatolMoney;

    private List activity ;


    public Integer getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Integer business_id) {
        this.business_id = business_id;
    }

    public Double getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(Double starting_price) {
        this.starting_price = starting_price;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Integer getSc_id() {
        return sc_id;
    }

    public void setSc_id(Integer sc_id) {
        this.sc_id = sc_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public BigDecimal getTatolMoney() {
        return tatolMoney;
    }

    public void setTatolMoney(BigDecimal tatolMoney) {
        this.tatolMoney = tatolMoney;
    }

    public List getActivity() {
        return activity;
    }

    public void setActivity(List activity) {
        this.activity = activity;
    }
}
