package com.shengxian.entity;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-17
 * @Version: 1.0
 */
public class Coupon {

    private Integer id;
    private Integer binding_id;
    private Integer coupon_id;
    private Double full;
    private Double reduce;
    private String startTime;
    private String endTime;

    public Coupon() {
    }

    public Coupon(Integer binding_id, Integer coupon_id, Double full, Double reduce, String startTime, String endTime) {
        this.binding_id = binding_id;
        this.coupon_id = coupon_id;
        this.full = full;
        this.reduce = reduce;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBinding_id() {
        return binding_id;
    }

    public void setBinding_id(Integer binding_id) {
        this.binding_id = binding_id;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Double getFull() {
        return full;
    }

    public void setFull(Double full) {
        this.full = full;
    }

    public Double getReduce() {
        return reduce;
    }

    public void setReduce(Double reduce) {
        this.reduce = reduce;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
