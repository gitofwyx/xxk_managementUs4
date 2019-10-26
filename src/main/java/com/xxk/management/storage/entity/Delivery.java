package com.xxk.management.storage.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Delivery extends BaseInfoEntity {
    private String id;
    private String entity_id;           //设备id
    private String stock_id;
    private String out_confirmed_ident; //出库编号
    private String out_confirmed_by;//确认人
    private String out_confirmed_officeId;//出库科室
    private String out_confirmed_date;    //出库时间
    private double out_confirmed_no;  //出库数量
    private String out_confirmed_unit;  //出库单位
    private int out_confirmed_proportion;  //个体/单位比例
    private double out_confirmed_total;      //出库人
    private String keyWord;           //关键字

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getOut_confirmed_ident() {
        return out_confirmed_ident;
    }

    public void setOut_confirmed_ident(String out_confirmed_ident) {
        this.out_confirmed_ident = out_confirmed_ident;
    }

    public String getOut_confirmed_by() {
        return out_confirmed_by;
    }

    public void setOut_confirmed_by(String out_confirmed_by) {
        this.out_confirmed_by = out_confirmed_by;
    }

    public String getOut_confirmed_officeId() {
        return out_confirmed_officeId;
    }

    public void setOut_confirmed_officeId(String out_confirmed_officeId) {
        this.out_confirmed_officeId = out_confirmed_officeId;
    }

    public String getOut_confirmed_date() {
        return out_confirmed_date;
    }

    public void setOut_confirmed_date(String out_confirmed_date) {
        this.out_confirmed_date = out_confirmed_date;
    }

    public double getOut_confirmed_no() {
        return out_confirmed_no;
    }

    public void setOut_confirmed_no(double out_confirmed_no) {
        this.out_confirmed_no = out_confirmed_no;
    }

    public String getOut_confirmed_unit() {
        return out_confirmed_unit;
    }

    public void setOut_confirmed_unit(String out_confirmed_unit) {
        this.out_confirmed_unit = out_confirmed_unit;
    }

    public int getOut_confirmed_proportion() {
        return out_confirmed_proportion;
    }

    public void setOut_confirmed_proportion(int out_confirmed_proportion) {
        this.out_confirmed_proportion = out_confirmed_proportion;
    }

    public double getOut_confirmed_total() {
        return out_confirmed_total;
    }

    public void setOut_confirmed_total(double out_confirmed_total) {
        this.out_confirmed_total = out_confirmed_total;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
