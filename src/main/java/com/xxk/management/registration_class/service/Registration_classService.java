package com.xxk.management.registration_class.service;

import com.xxk.management.registration_record.entity.Registration_record;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface Registration_classService {

    public List<Registration_record> listRegistration_record(int pageStart, int pageSize);

    public List<Registration_record> getRecordAccordRegistration(String registrationId,String officeId,String status);

    public List<Map<String, Object>> getRegistration_recordMakeDate(String registrationId, String[] status,String startDate, String endDate);

    public boolean addRegistration_record(Registration_record registration_record);

    public boolean addListRegistration_record(List<Registration_record> ListRecord);

}
