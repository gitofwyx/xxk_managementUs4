package com.xxk.management.registration.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Registration extends BaseInfoEntity {
    private String id;
    private String reg_office_id;      //登记科室ID
    private String exe_office_id;      //登记科室ID
    private String execute_id;      //登记ID
    private String reg_flag;      //登记状态
    private String reg_date;     //登记时间
    private String reg_type;      //登记类型
    private String reg_content;    //登记内容

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

    public String getExe_office_id() {
        return exe_office_id;
    }

    public void setExe_office_id(String exe_office_id) {
        this.exe_office_id = exe_office_id;
    }

    public String getExecute_id() {
        return execute_id;
    }

    public void setExecute_id(String execute_id) {
        this.execute_id = execute_id;
    }

    public String getReg_flag() {
        return reg_flag;
    }

    public void setReg_flag(String reg_flag) {
        this.reg_flag = reg_flag;
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
