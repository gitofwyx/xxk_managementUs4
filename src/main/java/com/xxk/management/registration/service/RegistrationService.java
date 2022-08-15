package com.xxk.management.registration.service;

import com.xxk.management.registration.entity.Registration;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface RegistrationService {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    public List<Map<String, Object>> listRegistrationMap(String reg_record_ident,
                                                         String reg_office_id,
                                                         String reg_record_status,
                                                         String execute_record_status,
                                                         int pageStart,
                                                         int pageSize,
                                                         String startDate,
                                                         String endDate);

    public Registration getRegistrationByRecordId(String recordId);

    public boolean addRegistration(Registration registration);

}
