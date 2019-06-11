package com.shengxian.entity;

/**
 * Description: 订单明细
 *
 * @Author: yang
 * @Date: 2019-02-16
 * @Version: 1.0
 */
public class OrderDetail {

    private Integer id;
    private Integer goods_id; //产品id
    private Integer order_id; //订单id
    private Double order_number; //购买数量
    private Double order_price; //每件产品的购买金额
    private int type; //0正常，1报损
    private double profit; //纯盈利
    private Double activity;
    private double cost_price;

    public OrderDetail() {
    }

    public OrderDetail(Integer goods_id, Integer order_id, Double order_number, Double order_price, int type, Double profit, Double activity) {
        this.goods_id = goods_id;
        this.order_id = order_id;
        this.order_number = order_number;
        this.order_price = order_price;
        this.type = type;
        this.profit = profit;
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Double getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Double order_number) {
        this.order_number = order_number;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Double getActivity() {
        return activity;
    }

    public void setActivity(Double activity) {
        this.activity = activity;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }
}
