package com.xxk.management.registration_class.entity;

import com.xxk.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Registration_class extends BaseInfoEntity {
    private String id;
    private String class_ident;           //登记编号
    private String class_reg_office;      //登记科室ID
    private String class_exe_office;      //执行科室ID
    private String class_exe_roles;      //执行角色ID
    private String class_add_date;      //添加时间
    private String reg_type;      //登记类型
    private String execute_level;      //执行等级
    private String reg_content;      //登记内容

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getClass_ident() {
        return class_ident;
    }

    public void setClass_ident(String class_ident) {
        this.class_ident = class_ident;
    }

    public String getClass_reg_office() {
        return class_reg_office;
    }

    public void setClass_reg_office(String class_reg_office) {
        this.class_reg_office = class_reg_office;
    }

    public String getClass_exe_office() {
        return class_exe_office;
    }

    public void setClass_exe_office(String class_exe_office) {
        this.class_exe_office = class_exe_office;
    }

    public String getClass_exe_roles() {
        return class_exe_roles;
    }

    public void setClass_exe_roles(String class_exe_roles) {
        this.class_exe_roles = class_exe_roles;
    }

    public String getClass_add_date() {
        return class_add_date;
    }

    public void setClass_add_date(String class_add_date) {
        this.class_add_date = class_add_date;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getExecute_level() {
        return execute_level;
    }

    public void setExecute_level(String execute_level) {
        this.execute_level = execute_level;
    }

    public String getReg_content() {
        return reg_content;
    }

    public void setReg_content(String reg_content) {
        this.reg_content = reg_content;
    }
}
