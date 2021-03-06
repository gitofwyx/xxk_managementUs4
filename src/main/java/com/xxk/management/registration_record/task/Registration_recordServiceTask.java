package com.xxk.management.registration_record.task;

import com.xxk.management.WeChat.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.registration.service.RegistrationMService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    @Scheduled(cron = "${schedule.task.test}")
    public void getRegistration_recordMakeDate() {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            String bootUrl="http://xxk-manage.nat300.top/";
            String markDownUrl=bootUrl+"reg?reg_id=";
            String[] status={"0","1"};
            String[] e_status={"0"};
            List<Map<String, Object>> listReg = registrationMService.listRegistrationUnionMap(null,
                    null,
                    null,
                    status,
                    e_status,
                    1,10
            );
            Map<String, Object> textMap = new HashMap<>();
            String text="最近十条未处理申请↓\n";
            List<String> strReg=listReg.stream().map(reg->
                    "<font color=\"info\">"+reg.get("reg_office") .toString()+"</font>:"
                            + reg.get("reg_record_content").toString()+"\n"
                            +"<font color=\"warning\">"+reg.get("reg_record_date") .toString()+"</font>"
                            +"\n"+"["+"点击查看详情和处理↑"+"]"
                            +"("+markDownUrl+reg.get("reg_record_id")+")")
                    .collect(Collectors.toList());
            if(strReg==null||strReg.isEmpty()){
                return;
            }

            String textReg=String.join("\n",strReg);
            textMap.put("content",text+textReg);
            weChatRobotService.chatBotSendByMarkdown(textMap);


        } catch (Exception e) {
            log.error(e);

        }
    }



}
