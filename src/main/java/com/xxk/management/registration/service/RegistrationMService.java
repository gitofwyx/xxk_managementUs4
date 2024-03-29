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

    public List<Map<String, Object>> listRegistrationUnionMap(String office_id,
                                                              String reg_record_py,
                                                              String reg_receiver_id,
                                                              String[] status,
                                                              String[] e_status,
                                                              int pageStart,
                                                              int pageSize);

    List<Map<String, Object>> listRegistrationMapAccordingDate(String office_id,
                                                               String reg_record_py,
                                                               String reg_receiver_id,
                                                               String[] status,
                                                               String[] e_status,
                                                               String startDate,
                                                               String endDate);

    public boolean addRegistrationAccordingRegStatus(Registration registration, Registration_record record) throws Exception, RuntimeException;

    public boolean updateRegistrationRegStatus(String id, String receiver_id,String reg_status) throws Exception, RuntimeException;

    public boolean acceptanceRegistration(String id,String receiver_id) throws Exception, RuntimeException;

}
