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

    public int countRegistration_record();

    public List<Registration_record> getRecordAccordRegistration(String registrationId,String officeId,String Status);

    public List<Registration_record> getRecordByRegistrationId(String registrationId,String Status);

    public List<Registration_record> getRecordByRegistrationIdInStatus(@Param("registration_id")String registration_id,
                                                                       @Param("statusList")String[] listStr);

    public List<Registration_record> getRegistration_recordByOffice(@Param("reg_record_py")String reg_record_py,
                                                                    @Param("office_id")String office_id,
                                                                    @Param("statusList")String[] listStr,
                                                                    @Param("startDate")String startDate,
                                                                    @Param("endDate")String endDate);

    public List<Registration_record> getRegistration_recordByReceiver(@Param("office_id")String office_id,
                                                                      @Param("statusList")String[] listStr,
                                                                      @Param("receiver_id")String receiver_id);

    public List<Map<String, Object>> getRegistration_recordById(String recordId);

    public int addRegistration_record(Registration_record registration_record);

    public int addListRegistration_record(List<Registration_record> ListRecord);

    public int updateRegistration_recordStatus(String id,String status, String receiver_id,String date);

    public int updateOnlyRegistration_recordStatus(String id,String status, String updateUserId,String date);

    public int updateRegistration_recordExeStatus(String id,String status, String updateUserId,String date);

    public int periodicUpdateRegistration_recordExeStatus(@Param("e_status")String e_status,
                                                          @Param("userId")String userId,
                                                          @Param("updateDate")String updateDate,
                                                          @Param("statusList")String[] listStr,
                                                          @Param("e_statusList")String[] e_listStr,
                                                          @Param("startDate")String startDate,
                                                          @Param("endDate")String endDate);

}
