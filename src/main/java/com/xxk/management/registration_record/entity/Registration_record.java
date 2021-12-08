package com.xxk.management.registration_record.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Registration_record extends BaseInfoEntity {
    private String id;
    private String reg_class_id;           //登记分类ID
    private String registration_id;      //登记ID
    private String examine_id;      //审核ID
    private String reg_record_py;      //登记记录人
    private String  reg_receiver_id;      //受理人
    private int reg_record_ident;      //登记记录编号
    private String reg_record_status;      //登记状态
    private String reg_record_date;      //登记时间
    private String reg_receiver_date;      //受理时间
    private String reg_record_type;      //登记类型
    private String execute_record_status;     //执行状态
    private String examine_record_status;      //审核状态
    private String reg_record_content;      //登记内容
    private String reg_record_describe;      //详细描述

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getReg_class_id() {
        return reg_class_id;
    }

    public void setReg_class_id(String reg_class_id) {
        this.reg_class_id = reg_class_id;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getExamine_id() {
        return examine_id;
    }

    public void setExamine_id(String examine_id) {
        this.examine_id = examine_id;
    }

    public String getReg_record_py() {
        return reg_record_py;
    }

    public void setReg_record_py(String reg_record_py) {
        this.reg_record_py = reg_record_py;
    }

    public String getReg_receiver_id() {
        return reg_receiver_id;
    }

    public void setReg_receiver_id(String reg_receiver_id) {
        this.reg_receiver_id = reg_receiver_id;
    }

    public int getReg_record_ident() {
        return reg_record_ident;
    }

    public void setReg_record_ident(int reg_record_ident) {
        this.reg_record_ident = reg_record_ident;
    }

    public String getReg_record_status() {
        return reg_record_status;
    }

    public void setReg_record_status(String reg_record_status) {
        this.reg_record_status = reg_record_status;
    }

    public String getReg_record_date() {
        return reg_record_date;
    }

    public void setReg_record_date(String reg_record_date) {
        this.reg_record_date = reg_record_date;
    }

    public String getReg_receiver_date() {
        return reg_receiver_date;
    }

    public void setReg_receiver_date(String reg_receiver_date) {
        this.reg_receiver_date = reg_receiver_date;
    }

    public String getReg_record_type() {
        return reg_record_type;
    }

    public void setReg_record_type(String reg_record_type) {
        this.reg_record_type = reg_record_type;
    }

    public String getExecute_record_status() {
        return execute_record_status;
    }

    public void setExecute_record_status(String execute_record_status) {
        this.execute_record_status = execute_record_status;
    }

    public String getExamine_record_status() {
        return examine_record_status;
    }

    public void setExamine_record_status(String examine_record_status) {
        this.examine_record_status = examine_record_status;
    }

    public String getReg_record_content() {
        return reg_record_content;
    }

    public void setReg_record_content(String reg_record_content) {
        this.reg_record_content = reg_record_content;
    }

    public String getReg_record_describe() {
        return reg_record_describe;
    }

    public void setReg_record_describe(String reg_record_describe) {
        this.reg_record_describe = reg_record_describe;
    }
}
