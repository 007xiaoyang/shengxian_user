package com.shengxian.entity;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-12
 * @Version: 1.0
 */
public class ComplaintsImg {

    private Integer id;
    private Integer complaints_id; //投诉id
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComplaints_id() {
        return complaints_id;
    }

    public void setComplaints_id(Integer complaints_id) {
        this.complaints_id = complaints_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
