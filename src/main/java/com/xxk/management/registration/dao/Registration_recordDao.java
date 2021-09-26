package com.xxk.management.registration.dao;

import com.xxk.management.registration.entity.Registration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Registration_recordDao {

    public List<Registration> listRegistration(int pageStart, int pageSize);

    public int addRegistration(Registration registration);

}
