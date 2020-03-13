package com.shengxian.entity;

import io.swagger.models.auth.In;

/**
 * @Description : Scheme
 * @Author: yang
 * @Date: 2020-03-09
 * @Version: 1.0
 */
public class Scheme {
    private Integer businessId ;
    private Integer schemeId ;


    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }
}
