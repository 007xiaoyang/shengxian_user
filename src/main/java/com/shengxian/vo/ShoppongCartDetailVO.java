package com.shengxian.vo;

/**
 * @Description : ShoppongCartDetailVO
 * @Author: yang
 * @Date: 2020-03-02
 * @Version: 1.0
 */
public class ShoppongCartDetailVO {
    private  Integer id;
    private Integer goodsId;
    private String img;
    private String name;
    private Double num;
    private Double price;
    private int status;
    private String units;
    private String type;

    private Integer shielding; // 是否屏蔽产品了
    private Integer restriction;  //限购
    private Double bestow; //
    private Double full;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getShielding() {
        return shielding;
    }

    public void setShielding(Integer shielding) {
        this.shielding = shielding;
    }

    public Integer getRestriction() {
        return restriction;
    }

    public void setRestriction(Integer restriction) {
        this.restriction = restriction;
    }

    public Double getBestow() {
        return bestow;
    }

    public void setBestow(Double bestow) {
        this.bestow = bestow;
    }

    public Double getFull() {
        return full;
    }

    public void setFull(Double full) {
        this.full = full;
    }
}
