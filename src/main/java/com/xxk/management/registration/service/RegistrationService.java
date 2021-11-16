package com.xxk.management.registration.service;

import com.xxk.management.registration.entity.Registration;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface RegistrationService {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    public Registration getRegistrationByRecordId(String recordId);

    public boolean addRegistration(Registration registration);

}
