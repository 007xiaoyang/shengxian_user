package com.shengxian.entity;

import java.util.Date;

/**
 * Description: 售货服务类
 *
 * @Author: yang
 * @Date: 2019-03-25
 * @Version: 1.0
 */
public class SalesService {

    private Integer id;
    private Integer binding_id; //用户注册id
    private Integer business_id; //投诉店铺id
    private String content; //投诉内容
    private String phone; //投诉者电话
    private Integer state;
    private Date create_time; //投诉时间

    private SalesServiceImg[] salesServiceImgs;
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

    public Integer getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Integer business_id) {
        this.business_id = business_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public SalesServiceImg[] getSalesServiceImgs() {
        return salesServiceImgs;
    }

    public void setSalesServiceImgs(SalesServiceImg[] salesServiceImgs) {
        this.salesServiceImgs = salesServiceImgs;
    }
}
