package com.shengxian.entity;

import java.util.Date;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-27
 * @Version: 1.0
 */
public class Order {
    private Integer id;
    private String order_number; //订单号
    private String no; //标识码
    private Integer binding_id; //用户id
    private Integer business_id; // 店铺id
    private Double price; // 总计金额
    private Double freight; // 运费
    private Date create_time;
    private String beizhu; // 备注
    private String making;//制单人
    private Integer part; //0线下，1商城

    private Integer address_id; //用户地址id
    private Integer coupon_id; //优惠券id
    private String name ; //优惠券名称，提交订单时根据名称来判断是店铺满减的还是个人优惠券
    private Integer staff_id ; //专员id 直接派送

    private long activity; //店铺满减活动id



    /**
     * 订单明细类数组
     */
    private OrderDetail[] orderDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getBinding_id() {
        return binding_id;
    }

    public void setBinding_id(Integer binding_id) {
        this.binding_id = binding_id;
    }

    public Integer getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Integer business_id) {
        this.business_id = business_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public long getActivity() {
        return activity;
    }

    public void setActivity(long activity) {
        this.activity = activity;
    }

    public OrderDetail[] getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail[] orderDetail) {
        this.orderDetail = orderDetail;
    }
}
