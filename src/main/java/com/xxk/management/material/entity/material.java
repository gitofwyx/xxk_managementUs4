package com.xxk.management.material.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Material extends BaseInfoEntity {
    private String id;
    private String mat_class_id;     //设备种类
    private String mat_name;      //设备名
    private String mat_ident;      //设备编号
    private String mat_type;    //设备型号
    private String mat_type_ident;    //型号编号
    private String single_unit;       //个体单位
    private String genre_tags;    //类型标识
    private String mat_flag;         //设备状态 -> 1:停用；0：正常
    private String remark;         //备注
    private String keyWord;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getMat_class_id() {
        return mat_class_id;
    }

    public void setMat_class_id(String mat_class_id) {
        this.mat_class_id = mat_class_id;
    }

    public String getMat_name() {
        return mat_name;
    }

    public void setMat_name(String mat_name) {
        this.mat_name = mat_name;
    }

    public String getMat_ident() {
        return mat_ident;
    }

    public void setMat_ident(String mat_ident) {
        this.mat_ident = mat_ident;
    }

    public String getMat_type() {
        return mat_type;
    }

    public void setMat_type(String mat_type) {
        this.mat_type = mat_type;
    }

    public String getMat_type_ident() {
        return mat_type_ident;
    }

    public void setMat_type_ident(String mat_type_ident) {
        this.mat_type_ident = mat_type_ident;
    }

    public String getSingle_unit() {
        return single_unit;
    }

    public void setSingle_unit(String single_unit) {
        this.single_unit = single_unit;
    }

    public String getGenre_tags() {
        return genre_tags;
    }

    public void setGenre_tags(String genre_tags) {
        this.genre_tags = genre_tags;
    }

    public String getMat_flag() {
        return mat_flag;
    }

    public void setMat_flag(String mat_flag) {
        this.mat_flag = mat_flag;
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
