package com.xxk.management.registration_class.dao;

import com.xxk.management.registration_record.entity.Registration_record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Registration_classDao {

    public List<Registration_record> listRegistration_record(int pageStart, int pageSize);

    public List<Registration_record> getRecordAccordRegistration(String registrationId,String officeId,String Status);

    public List<Registration_record> getRegistration_recordByOffice(@Param("office_id")String office_id,
                                                                    @Param("statusList")String[] listStr);

    public int addRegistration_record(Registration_record registration_record);

    public int addListRegistration_record(List<Registration_record> ListRecord);

}
