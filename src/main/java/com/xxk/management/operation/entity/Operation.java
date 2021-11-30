package com.xxk.management.operation.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Operation extends BaseInfoEntity {
    private String id;
    private String ope_ident;              //运维编号
    private String ope_registration_id;  //登记ID
    private String ope_class_id;            //运维分类ID
    private String ope_staff_id;          //运维人员ID
    private String ope_office_id;         //运维科室ID
    private String ope_device_id;         //运维设备ID
    private String ope_auditing_id;       //审核ID
    private String ope_source;             //运维来源
    private String ope_statement;                //运维声明
    private String ope_type;                //运维类型
    private String ope_confirm_date;      //运维确认时间
    private String ope_content;            //运维内容
    private String ope_record_describe;            //运维详细
    private String ope_flag;               //运维状态
    private String ope_result;             //运维结果
    private String ope_suggest;             //运维建议
    private String remark;                 //备注
    private String keyWord;                //关键字

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getOpe_ident() {
        return ope_ident;
    }

    public void setOpe_ident(String ope_ident) {
        this.ope_ident = ope_ident;
    }

    public String getOpe_registration_id() {
        return ope_registration_id;
    }

    public void setOpe_registration_id(String ope_registration_id) {
        this.ope_registration_id = ope_registration_id;
    }

    public String getOpe_class_id() {
        return ope_class_id;
    }

    public void setOpe_class_id(String ope_class_id) {
        this.ope_class_id = ope_class_id;
    }

    public String getOpe_staff_id() {
        return ope_staff_id;
    }

    public void setOpe_staff_id(String ope_staff_id) {
        this.ope_staff_id = ope_staff_id;
    }

    public String getOpe_office_id() {
        return ope_office_id;
    }

    public void setOpe_office_id(String ope_office_id) {
        this.ope_office_id = ope_office_id;
    }

    public String getOpe_device_id() {
        return ope_device_id;
    }

    public void setOpe_device_id(String ope_device_id) {
        this.ope_device_id = ope_device_id;
    }

    public String getOpe_auditing_id() {
        return ope_auditing_id;
    }

    public void setOpe_auditing_id(String ope_auditing_id) {
        this.ope_auditing_id = ope_auditing_id;
    }

    public String getOpe_source() {
        return ope_source;
    }

    public void setOpe_source(String ope_source) {
        this.ope_source = ope_source;
    }

    public String getOpe_statement() {
        return ope_statement;
    }

    public void setOpe_statement(String ope_statement) {
        this.ope_statement = ope_statement;
    }

    public String getOpe_type() {
        return ope_type;
    }

    public void setOpe_type(String ope_type) {
        this.ope_type = ope_type;
    }

    public String getOpe_confirm_date() {
        return ope_confirm_date;
    }

    public void setOpe_confirm_date(String ope_confirm_date) {
        this.ope_confirm_date = ope_confirm_date;
    }

    public String getOpe_content() {
        return ope_content;
    }

    public void setOpe_content(String ope_content) {
        this.ope_content = ope_content;
    }

    public String getOpe_record_describe() {
        return ope_record_describe;
    }

    public void setOpe_record_describe(String ope_record_describe) {
        this.ope_record_describe = ope_record_describe;
    }

    public String getOpe_flag() {
        return ope_flag;
    }

    public void setOpe_flag(String ope_flag) {
        this.ope_flag = ope_flag;
    }

    public String getOpe_result() {
        return ope_result;
    }

    public void setOpe_result(String ope_result) {
        this.ope_result = ope_result;
    }

    public String getOpe_suggest() {
        return ope_suggest;
    }

    public void setOpe_suggest(String ope_suggest) {
        this.ope_suggest = ope_suggest;
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
