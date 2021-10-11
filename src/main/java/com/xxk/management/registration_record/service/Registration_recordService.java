package com.xxk.management.registration_record.service;

import com.xxk.management.registration_record.entity.Registration_record;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface Registration_recordService {

    public List<Registration_record> listRegistration_record(int pageStart, int pageSize);

    public boolean addRegistration_record(Registration_record registration_record);

}
