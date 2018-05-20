package com.xxk.management.operation.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class OpeRecord extends BaseInfoEntity {
    private String id;
    private String ope_office_id;     //运维科室ID
    private String ope_count;      //运维次数

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getOpe_office_id() {
        return ope_office_id;
    }

    public void setOpe_office_id(String ope_office_id) {
        this.ope_office_id = ope_office_id;
    }

    public String getOpe_count() {
        return ope_count;
    }

    public void setOpe_count(String ope_count) {
        this.ope_count = ope_count;
    }
}
