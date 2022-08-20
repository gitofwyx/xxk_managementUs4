package com.xxk.management.bar_code.entity;

import com.xxk.core.entity.BaseInfoEntity;

public class Bar_code extends BaseInfoEntity {
    private String id;
    private String entity_id;     //设备ID
    private String entities_id;      //设备ID
    private String offices_id;      //科室ID
    private String station_id;      //工作站ID
    private String bar_code;      //bar_code
    private String bar_code_ident;      //条形码编号
    private String bar_code_type;    //条形码种类
    private int bar_code_genre;    //条形码类别
    private String bar_code_by;      //确认人
    private String bar_code_officeId;     //条形码科室ID
    private String bar_code_date;         //添加时间
    private String bar_code_status;        //条形码状态
    private String keyWord;

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

    public String getEntities_id() {
        return entities_id;
    }

    public void setEntities_id(String entities_id) {
        this.entities_id = entities_id;
    }

    public String getOffices_id() {
        return offices_id;
    }

    public void setOffices_id(String offices_id) {
        this.offices_id = offices_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getBar_code_ident() {
        return bar_code_ident;
    }

    public void setBar_code_ident(String bar_code_ident) {
        this.bar_code_ident = bar_code_ident;
    }

    public String getBar_code_type() {
        return bar_code_type;
    }

    public void setBar_code_type(String bar_code_type) {
        this.bar_code_type = bar_code_type;
    }

    public int getBar_code_genre() {
        return bar_code_genre;
    }

    public void setBar_code_genre(int bar_code_genre) {
        this.bar_code_genre = bar_code_genre;
    }

    public String getBar_code_by() {
        return bar_code_by;
    }

    public void setBar_code_by(String bar_code_by) {
        this.bar_code_by = bar_code_by;
    }

    public String getBar_code_officeId() {
        return bar_code_officeId;
    }

    public void setBar_code_officeId(String bar_code_officeId) {
        this.bar_code_officeId = bar_code_officeId;
    }

    public String getBar_code_date() {
        return bar_code_date;
    }

    public void setBar_code_date(String bar_code_date) {
        this.bar_code_date = bar_code_date;
    }

    public String getBar_code_status() {
        return bar_code_status;
    }

    public void setBar_code_status(String bar_code_status) {
        this.bar_code_status = bar_code_status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
