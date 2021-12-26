package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.websocket.web.ComWebSocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class RegistrationMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationMService registrationMService;

    @Autowired
    private ComWebSocketHandler comWebSocketHandler;

    @ResponseBody
    @RequestMapping("/addMobileRegistration")
    public Map<String, Object> addRegistration(Registration registration, Registration_record record) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        int reg_count = 0;
        try {
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            String CurrentUser = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
            registration.setRegistration_py(CurrentUserId);
            boolean resultReg=registrationMService.addRegistrationAccordingRegStatus(registration,record);
            if(resultReg){
                comWebSocketHandler.sendMessageToUsers(new TextMessage("有新的申请单已提交，提交人："+CurrentUser));
            }

        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "更新数据出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
