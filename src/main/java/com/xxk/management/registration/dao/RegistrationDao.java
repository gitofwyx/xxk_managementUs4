package com.xxk.management.registration.dao;

import com.xxk.management.registration.entity.Registration;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface RegistrationDao {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    public List<Map<String, Object>> listRegistrationUnionMap(@Param("reg_record_ident")String reg_record_ident,
                                                              @Param("office_id")String office_id,
                                                              @Param("reg_record_py")String reg_record_py,
                                                              @Param("reg_receiver_id")String reg_receiver_id,
                                                              @Param("statusList")String[] listStr,
                                                              @Param("pageStart")int pageStart,
                                                              @Param("pageSize")int pageSize);

    public Registration getRegistrationForRegStatus(String registrationId,String Status);

    public Registration getRegistrationByRecordId(String recordId);

    public int addRegistration(Registration registration);

    public int updateRegistrationStatus(String id, String receiver_id,String date,String status);


}
