package com.xxk.management.registration_record.dao;

import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration_record.entity.Registration_record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Registration_recordDao {

    public List<Registration_record> listRegistration_record(int pageStart, int pageSize);

    public List<Registration_record> getRecordAccordRegistration(String registrationId,String officeId,String Status);

    public List<Registration_record> getRegistration_recordByOffice(@Param("office_id")String office_id,
                                                                    @Param("statusList")String[] listStr);

    public List<Map<String, Object>> getRegistration_recordById(String recordId);

    public int addRegistration_record(Registration_record registration_record);

    public int addListRegistration_record(List<Registration_record> ListRecord);

    public int updateRegistration_recordStatus(String id,String status, String receiver_id,String date);

}
