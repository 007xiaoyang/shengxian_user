package com.shengxian.vo;


import java.util.List;

/**
 * @Description : GoodsCategoryVO
 * @Author: yang
 * @Date: 2020-03-08
 * @Version: 1.0
 */
public class GoodsCategoryVO {

    private Long id ;
    private Long businessId;
    private String  name;
    private Integer level;
    private String code;
    private Integer status;
    private List<GoodsCategoryVO> children ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<GoodsCategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsCategoryVO> children) {
        this.children = children;
    }
}
