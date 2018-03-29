package com.xxk.management.offices.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Offices extends BaseInfoEntity {
    private String id;
    private int office_ident;
    private String belong_to_id;     //所属科室id
    private String office_name;      //科室名
    private String leading_official;      //科室负责人
    private String keyWord;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public int getOffice_ident() {
        return office_ident;
    }

    public void setOffice_ident(int office_ident) {
        this.office_ident = office_ident;
    }

    public String getBelong_to_id() {
        return belong_to_id;
    }

    public void setBelong_to_id(String belong_to_id) {
        this.belong_to_id = belong_to_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getLeading_official() {
        return leading_official;
    }

    public void setLeading_official(String leading_official) {
        this.leading_official = leading_official;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
