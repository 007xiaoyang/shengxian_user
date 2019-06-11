package com.shengxian.entity;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-21
 * @Version: 1.0
 */
public class Parameter {

    private Integer binding_id;
    private String status ;
    private Integer state;
    private Integer type;
    private Integer startIndex;
    private Integer pageSize;
    private String startTime ;
    private String endTime ;
    private Integer mold ;

    public Parameter() {
    }

    public Parameter(Integer binding_id, String status, Integer state, Integer type ,String startTime ,String endTime , Integer mold) {
        this.binding_id = binding_id;
        this.status = status;
        this.state = state;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mold = mold;
    }

    public Parameter(Integer binding_id, String status, Integer state, Integer type ,String startTime ,String endTime,Integer mold , Integer startIndex, Integer pageSize) {
        this.binding_id = binding_id;
        this.status = status;
        this.state = state;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mold = mold;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }
}
