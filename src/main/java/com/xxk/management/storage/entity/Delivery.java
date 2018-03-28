package com.xxk.management.storage.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Delivery extends BaseInfoEntity {
    private String id;
    private String device_id;           //设备id
    private String out_confirmed_ident; //出库编号
    private String use_offices;    //领用科室
    private int used_no;  //领用数量
    private String used_name;      //领用人
    private String  used_date;         //领用时间
    private String keyWord;           //关键字

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOut_confirmed_ident() {
        return out_confirmed_ident;
    }

    public void setOut_confirmed_ident(String out_confirmed_ident) {
        this.out_confirmed_ident = out_confirmed_ident;
    }

    public String getUse_offices() {
        return use_offices;
    }

    public void setUse_offices(String use_offices) {
        this.use_offices = use_offices;
    }

    public int getUsed_no() {
        return used_no;
    }

    public void setUsed_no(int used_no) {
        this.used_no = used_no;
    }

    public String getUsed_name() {
        return used_name;
    }

    public void setUsed_name(String used_name) {
        this.used_name = used_name;
    }

    public String getUsed_date() {
        return used_date;
    }

    public void setUsed_date(String used_date) {
        this.used_date = used_date;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
