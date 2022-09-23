package com.xxk.management.bar_code.dao;

import com.xxk.management.bar_code.entity.Bar_code;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.bar_code.entity.Bar_code;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Bar_codeDao {

    public List<Bar_code> listDevice(int pageStart, int pageSize);

    public int countDevice();

    public Bar_code getBar_codeByBar_code_ident(String bar_code_ident);

    public List<Bar_code> lisBar_codeByINSTRBar_code_ident(String bar_code_ident);

    public int addBar_code(Bar_code bar_code);

}
