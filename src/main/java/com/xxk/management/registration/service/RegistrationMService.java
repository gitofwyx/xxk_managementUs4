package com.xxk.management.registration.service;

import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.entity.Registration_record;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface RegistrationMService {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    public Map<String, Object> addRegistration(Registration registration, Registration_record record) throws Exception, RuntimeException;

}
