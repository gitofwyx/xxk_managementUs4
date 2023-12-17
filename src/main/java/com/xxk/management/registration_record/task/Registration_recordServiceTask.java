package com.xxk.management.registration_record.task;

import com.xxk.core.util.DateUtil;
import com.xxk.management.WeChat.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/15.
 */
@Lazy(false)
@Configuration
@EnableScheduling
public class Registration_recordServiceTask {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private WeChatRobotService weChatRobotService;

    @Autowired
    private RegistrationMService registrationMService;

    @Autowired
    private Registration_recordService registration_recordService;

    @Scheduled(cron = "${schedule.task.getRegNotFinished}")
    public void getRegistration_recordMakeDate() {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            String bootUrl = "https://xxk-manage.mynatapp.cc/";
            String markDownUrl = bootUrl + "reg?reg_id=";
            String[] status = {"0", "1"};
            String[] e_status = {"0"};
            List<Map<String, Object>> listReg = registrationMService.listRegistrationUnionMap(null,
                    null,
                    null,
                    status,
                    e_status,
                    1, 10
            );
            Map<String, Object> textMap = new HashMap<>();
            String text = "↓最近十条未处理申请↓\n";
            List<String> strReg = listReg.stream().map(reg ->
                    "<font color=\"info\">" + reg.get("reg_office").toString() + "</font>:"
                            + "[" + reg.get("reg_record_content").toString() + "]"
                            + "(" + markDownUrl + reg.get("reg_record_id") + ")" + "\n"
                            + "<font color=\"warning\">↑" + reg.get("reg_record_date").toString() + "↑</font>")
                    .collect(Collectors.toList());
            if (strReg == null || strReg.isEmpty()) {
                return;
            }

            String textReg = String.join("\n", strReg);
            textMap.put("content", text + textReg);
            weChatRobotService.chatBotSendByMarkdown(textMap);


        } catch (Exception e) {
            log.error(e);

        }
    }

    @Scheduled(cron = "${schedule.task.periodicUpdateExeStatus}")
    public void periodicUpdateRegistration_recordExeStatus() {
        try {
            String Date = DateUtil.getFullTime();
            String[] status = {"0", "1"};
            String[] e_status = {"1"};
            String BeforeDate=DateUtil.getBeforeAfterDate(Date,-31);
            String startDate=BeforeDate+" 00:00:00";
            String endDate= BeforeDate+" 23:59:59";
            int Result = registration_recordService.periodicUpdateRegistration_recordExeStatus(
                    "2",
                    "auto_finish",
                    Date,
                    status,
                    e_status,
                    startDate,
                    endDate);
            log.warn("申请单定时检查"+Date+":"+Result+"条已更新。");

        } catch (Exception e) {
            log.error(e);
            /*Optional.ofNullable(e).ifPresent(e1 -> {
                result.put("error",e1.getLocalizedMessage());
            });*/
        }
        //return "system/index";
    }

}
