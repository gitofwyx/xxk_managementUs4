package com.xxk.management.registration.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Registration extends BaseInfoEntity {
    private String id;
    private String reg_office_id;      //运维人员ID
    private String reg_date;     //运维科室ID
    private String reg_type;      //运维设备ID
    private String reg_content;    //审核ID

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getReg_office_id() {
        return reg_office_id;
    }

    public void setReg_office_id(String reg_office_id) {
        this.reg_office_id = reg_office_id;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getReg_content() {
        return reg_content;
    }

    public void setReg_content(String reg_content) {
        this.reg_content = reg_content;
    }
}
