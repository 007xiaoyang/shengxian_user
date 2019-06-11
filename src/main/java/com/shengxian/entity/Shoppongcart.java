package com.shengxian.entity;

import java.util.Date;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-20
 * @Version: 1.0
 */
public class Shoppongcart {

    private Integer id ;
    private Integer binding_id;
    private Date create_time;

    public Shoppongcart() {
    }

    public Shoppongcart(Integer binding_id, Date create_time) {
        this.binding_id = binding_id;
        this.create_time = create_time;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
