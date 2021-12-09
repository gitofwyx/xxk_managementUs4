package com.xxk.management.registration.service;

import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration_record.entity.Registration_record;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface RegistrationMService {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    List<Map<String, Object>> listRegistrationMapAccordingDate(String office_id,
                                                               String reg_record_py,
                                                               String reg_receiver_id,
                                                               String[] status,
                                                               String[] e_status);

    public Map<String, Object> addRegistrationAccordingRegStatus(Registration registration, Registration_record record) throws Exception, RuntimeException;

    public boolean acceptanceRegistration(String id,String receiver_id) throws Exception, RuntimeException;

}
