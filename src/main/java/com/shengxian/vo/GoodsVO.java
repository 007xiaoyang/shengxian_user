package com.shengxian.vo;

/**
 * @Description : GoodsVO
 * @Author: yang
 * @Date: 2020-03-13
 * @Version: 1.0
 */
public class GoodsVO {

    private Long id ;
    private String name ;
    private String units;
    private String spec;
    private String img;

    private Long scdId;
    private Long scId;
    private Double num;
    private Double  price;
    private Double full;    //满
    private Double bestow ; //赠
    private  Double limitedQuantity; //限制数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getScdId() {
        return scdId;
    }

    public void setScdId(Long scdId) {
        this.scdId = scdId;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFull() {
        return full;
    }

    public void setFull(Double full) {
        this.full = full;
    }

    public Double getBestow() {
        return bestow;
    }

    public void setBestow(Double bestow) {
        this.bestow = bestow;
    }

    public Double getLimitedQuantity() {
        return limitedQuantity;
    }

    public void setLimitedQuantity(Double limitedQuantity) {
        this.limitedQuantity = limitedQuantity;
    }
}
