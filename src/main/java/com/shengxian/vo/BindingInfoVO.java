package com.shengxian.vo;

/**
 * @Description : BindingInfoVO
 * @Author: yang
 * @Date: 2020-03-11
 * @Version: 1.0
 */
public class BindingInfoVO {

    private Integer businessId;
    private String storeName ; //店铺名称
    private String img ; //店铺图片
    private String userName ; //绑定人姓名
    private Integer id ; //绑定人id
    private String telephone ; //店铺电话
    private String notice; //店铺公告
    private String staffPhone; //专员电话
    private String staffName;   //专员名称

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
