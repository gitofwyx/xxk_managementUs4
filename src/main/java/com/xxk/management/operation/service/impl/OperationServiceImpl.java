package com.xxk.management.operation.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.WeChat.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.operation.dao.OperationDao;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OperationService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class OperationServiceImpl implements OperationService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private OperationDao dao;

    @Autowired
    private Registration_recordService registration_recordService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private WeChatRobotService weChatRobotService;

    @Override
    public List<Operation> listOperation(int pageStart, int pageSize) {
        return dao.listOperation((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<Map<String, Object>> listOperationAccordingDate(String registration_id, String[] status) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        try {
            List<Map<String, Object>> listOpe = dao.listOperationByRegId(registration_id, status);
            for (Map<String, Object> ope : listOpe) {
                if (ope.get("ope_confirm_date") == null) {
                    continue;
                }
                String recordDate = ope.get("ope_confirm_date").toString().substring(0, 10);
                if (dateList.contains(recordDate)) {
                    continue;
                }
                dateList.add(recordDate);
                Map<String, Object> resultReg = new HashMap<>();
                List<Map<String, Object>> recordList = new ArrayList<>();
                for (Map<String, Object> opeMap : listOpe) {
                    if (recordDate.equals(opeMap.get("ope_confirm_date").toString().substring(0, 10))) {
                        recordList.add(opeMap);
                    }
                }
                resultReg.put(recordDate, recordList);
                resultList.add(resultReg);
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }

        return resultList;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean addOperation(Operation operation,Map<String, Object> reg_recordMap) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> markdownMap=new HashMap<>();
        Boolean resultReg = false;
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        List<Registration_record> listRecord = new ArrayList();
        //String bootUrl="http://192.168.3.40:8080/";
        String bootUrl="https://xxk-manage.mynatapp.cc/";
        String markDownUrl="("+bootUrl+"reg?reg_id="+operation.getOpe_registration_id()+")";
        String contentText1="";
        String contentText2="";
        int reg_count = 0;
        Registration reg =registrationService.getRegistrationByRecordId(operation.getOpe_registration_id());
        operation.setOpe_office_id("NO");
        if(reg!=null){
            operation.setOpe_office_id(reg.getExe_office_id());
        }
        List<Map<String, Object>> reg_records=registration_recordService.getRegistration_recordById(operation.getOpe_registration_id());
        if(reg_records!=null && reg_records.size()>0 && reg_records.get(0)!=null){
            if("2".equals(reg_records.get(0).get("execute_record_status"))){
                log.error("addRegistration:当前记录已处理完！");
                throw new Exception("addRegistration:当前记录已处理完！");
            }
            contentText1="由"+" <font color=\"warning\">"+reg_recordMap.get("reg_record_name")+ "</font> 提交于\n<font color=\"warning\">"+
                    reg_recordMap.get("reg_record_date")+"</font>"+"\n(NO.<font color=\"warning\">"+ reg_recordMap.get("reg_record_ident")+
                    "</font>)的申请单：\n<font color=\"info\">"+ reg_recordMap.get("reg_record_content")+"</font>\n";
            contentText2="<font color=\"info\">"+operation.getOpe_content()+"</font>\n由<font color=\"warning\">"+reg_recordMap.get("CurrentUser")+
                    "</font>提交于<font color=\"warning\">"+Date+"</font>\n[点击登录后可查看详细和处理。]"+markDownUrl;
            if("3".equals(operation.getOpe_statement())&&"0".equals(reg_records.get(0).get("execute_record_status"))){
                registration_recordService.updateRegistration_recordExeStatus(operation.getOpe_registration_id(),"1",operation.getCreateUserId(),Date);
                operation.setOpe_flag("1");
                markdownMap.put("content",contentText1+"已提交新的处理流程:\n"+contentText2);

            }else if("1".equals(reg_records.get(0).get("execute_record_status"))){
                operation.setOpe_flag("1");
            }
            else {operation.setOpe_flag("0");}
            if("0".equals(reg_records.get(0).get("reg_record_status"))){
                registration_recordService.updateRegistration_recordStatus(operation.getOpe_registration_id(), "1", operation.getCreateUserId(), Date);
            }

        }else {
            log.error("addRegistration:registration_recordService.getRegistration_recordById为空！");
            throw new Exception("addRegistration:registration_recordService.getRegistration_recordById为空！");
        }
        operation.setId(recId);
        operation.setOpe_ident("NO");
        operation.setOpe_staff_id(operation.getCreateUserId());
        operation.setOpe_confirm_date(Date);
        operation.setUpdateUserId(operation.getCreateUserId());
        operation.setUpdateDate(Date);
        operation.setDeleteFlag("0");
        resultReg = dao.addOperation(operation)==1 ? true : false;
        if (!(resultReg)) {
            log.error("addRegistration:registration_recordService.addRegistration_record出错！");
            throw new Exception("addRegistration:registration_recordService.addRegistration_record出错！");
        }

        if("1".equals(operation.getOpe_statement())){
            markdownMap.put("content",contentText1+"已提交新的处理调查:\n"+contentText2);
        }
        else if("2".equals(operation.getOpe_statement())){
            markdownMap.put("content",contentText1+"已提交新的处理建议:\n"+contentText2);
        }
        else if("4".equals(operation.getOpe_statement())){
            markdownMap.put("content",contentText1+"已提交新的处理反馈:\n"+contentText2);
        }

        weChatRobotService.chatBotSendByMarkdown(markdownMap);

        return resultReg;
    }

    @Override
    public List<Map<String, Object>> getOfficeSelect() {
        return dao.getOfficeSelect();
    }

    @Override
    public int getUnderlingCount(String belong_to_id) {
        return dao.getUnderlingCount(belong_to_id);
    }

    @Override
    public int geRootCount(String belong_to_id) {
        return dao.geRootCount(belong_to_id);
    }
}
