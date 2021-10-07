package com.xxk.management.registration.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Registration extends BaseInfoEntity {
    private String id;
    private String reg_ident;           //登记编号
    private String reg_office_id;      //登记科室ID
    private String exe_office_id;      //执行科室ID
    private String reg_class_id;      //登记分类ID
    private String registration_py;      //登记人员ID
    private String execute_id;      //登记ID
    private String examine_id;      //审核ID
    private String reg_status;      //登记状态
    private String reg_date;     //登记时间
    private String reg_type;      //登记类型
    private String execute_status;      //执行状态
    private String examine_status;      //审核状态
    private String reg_content;    //登记内容

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getReg_ident() {
        return reg_ident;
    }

    public void setReg_ident(String reg_ident) {
        this.reg_ident = reg_ident;
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

    public String getReg_class_id() {
        return reg_class_id;
    }

    public void setReg_class_id(String reg_class_id) {
        this.reg_class_id = reg_class_id;
    }

    public String getRegistration_py() {
        return registration_py;
    }

    public void setRegistration_py(String registration_py) {
        this.registration_py = registration_py;
    }

    public String getExecute_id() {
        return execute_id;
    }

    public void setExecute_id(String execute_id) {
        this.execute_id = execute_id;
    }

    public String getExamine_id() {
        return examine_id;
    }

    public void setExamine_id(String examine_id) {
        this.examine_id = examine_id;
    }

    public String getReg_status() {
        return reg_status;
    }

    public void setReg_status(String reg_status) {
        this.reg_status = reg_status;
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

    public String getExecute_status() {
        return execute_status;
    }

    public void setExecute_status(String execute_status) {
        this.execute_status = execute_status;
    }

    public String getExamine_status() {
        return examine_status;
    }

    public void setExamine_status(String examine_status) {
        this.examine_status = examine_status;
    }

    public String getReg_content() {
        return reg_content;
    }

    public void setReg_content(String reg_content) {
        this.reg_content = reg_content;
    }
}
