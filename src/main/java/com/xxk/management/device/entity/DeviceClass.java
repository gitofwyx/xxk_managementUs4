package com.xxk.management.device.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class DeviceClass extends BaseInfoEntity {
    private String id;
    private String dev_class;     //设备种类
    private int class_ident;     //设备种类编号
    private String class_tab;     //大类标识
    private int dev_max;      //设备最大值
    private int type_max;      //种类最大值

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDev_class() {
        return dev_class;
    }

    public void setDev_class(String dev_class) {
        this.dev_class = dev_class;
    }

    public int getClass_ident() {
        return class_ident;
    }

    public void setClass_ident(int class_ident) {
        this.class_ident = class_ident;
    }

    public String getClass_tab() {
        return class_tab;
    }

    public void setClass_tab(String class_tab) {
        this.class_tab = class_tab;
    }

    public int getDev_max() {
        return dev_max;
    }

    public void setDev_max(int dev_max) {
        this.dev_max = dev_max;
    }

    public int getType_max() {
        return type_max;
    }

    public void setType_max(int type_max) {
        this.type_max = type_max;
    }
}
