package com.xxk.management.registration_record.dao;

import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration_record.entity.Registration_record;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Registration_recordDao {

    public List<Registration_record> listRegistration_record(int pageStart, int pageSize);

    public List<Registration_record> getRegistration_recordForRegStatus(String registrationId,String Status);

    public int addRegistration_record(Registration_record registration_record);

    public int addListRegistration_record(List<Registration_record> ListRecord);

}
