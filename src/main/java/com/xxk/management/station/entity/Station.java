package com.xxk.management.station.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Station extends BaseInfoEntity {
    private String id;
    private int workstation_ident;        //工作站编号
    private String workstation_name;     //工作站专有名称
    private String workstation_state;     //工作站状态
    private String location_office_id;      //现在科室id
    private String mediaOfLANId;      //通信介质id
    private String workstation_deployment_status;      //部署状态
    private String workstation_deployment_date;      //部署时间
    private String related_flag;                      //关联状态
    private String workstation_related_id;                      //关联状态
    private String workstation_location;              //工作站具体位置
    private String workstation_flag;                  //工作站标识
    private String workstation_auditing_mark;         //工作站审核标志
    private String reject_date;                       //报废时间
    private String remark;                            //备注
    private String keyWord;                           //关键字

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public int getWorkstation_ident() {
        return workstation_ident;
    }

    public void setWorkstation_ident(int workstation_ident) {
        this.workstation_ident = workstation_ident;
    }

    public String getWorkstation_name() {
        return workstation_name;
    }

    public void setWorkstation_name(String workstation_name) {
        this.workstation_name = workstation_name;
    }

    public String getWorkstation_state() {
        return workstation_state;
    }

    public void setWorkstation_state(String workstation_state) {
        this.workstation_state = workstation_state;
    }

    public String getLocation_office_id() {
        return location_office_id;
    }

    public void setLocation_office_id(String location_office_id) {
        this.location_office_id = location_office_id;
    }

    public String getMediaOfLANId() {
        return mediaOfLANId;
    }

    public void setMediaOfLANId(String mediaOfLANId) {
        this.mediaOfLANId = mediaOfLANId;
    }

    public String getWorkstation_deployment_status() {
        return workstation_deployment_status;
    }

    public void setWorkstation_deployment_status(String workstation_deployment_status) {
        this.workstation_deployment_status = workstation_deployment_status;
    }

    public String getWorkstation_deployment_date() {
        return workstation_deployment_date;
    }

    public void setWorkstation_deployment_date(String workstation_deployment_date) {
        this.workstation_deployment_date = workstation_deployment_date;
    }

    public String getRelated_flag() {
        return related_flag;
    }

    public void setRelated_flag(String related_flag) {
        this.related_flag = related_flag;
    }

    public String getWorkstation_related_id() {
        return workstation_related_id;
    }

    public void setWorkstation_related_id(String workstation_related_id) {
        this.workstation_related_id = workstation_related_id;
    }

    public String getWorkstation_location() {
        return workstation_location;
    }

    public void setWorkstation_location(String workstation_location) {
        this.workstation_location = workstation_location;
    }

    public String getWorkstation_flag() {
        return workstation_flag;
    }

    public void setWorkstation_flag(String workstation_flag) {
        this.workstation_flag = workstation_flag;
    }

    public String getWorkstation_auditing_mark() {
        return workstation_auditing_mark;
    }

    public void setWorkstation_auditing_mark(String workstation_auditing_mark) {
        this.workstation_auditing_mark = workstation_auditing_mark;
    }

    public String getReject_date() {
        return reject_date;
    }

    public void setReject_date(String reject_date) {
        this.reject_date = reject_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
