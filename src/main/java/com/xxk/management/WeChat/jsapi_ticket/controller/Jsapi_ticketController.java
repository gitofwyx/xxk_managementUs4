package com.xxk.management.WeChat.jsapi_ticket.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.DecriptUtil;
import com.xxk.core.util.StringUtil;
import com.xxk.management.WeChat.access_token.constants.Access_tokenConstants;
import com.xxk.management.WeChat.jsapi_ticket.entity.Jsapi_ticket;
import com.xxk.management.WeChat.jsapi_ticket.service.Jsapi_ticketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class Jsapi_ticketController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private Jsapi_ticketService jsapi_ticketService;

    @ResponseBody
    @RequestMapping("/makeSDKSignature")
    public Map<String, Object> listDeliveryByStock(@RequestParam(value = "url") String url) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();
        List<Map<String, Object>> userMapList = new ArrayList<>();


        try {
            Jsapi_ticket jsapi_ticket=jsapi_ticketService.makeJsapi_ticket();
            if(jsapi_ticket!=null){
                String noncestr= StringUtil.getRandom(null,16);
                String timestamp= DateUtil.dateToStamp(DateUtil.getFullTime());
                String signature=DecriptUtil.SHA1("jsapi_ticket="+jsapi_ticket.getTicket()+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url);
                result.put("debug", true);
                result.put("appId", Access_tokenConstants.APPID);
                result.put("timestamp", timestamp);
                result.put("nonceStr", noncestr);
                result.put("signature",signature);
            }


        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "查询出错");
        }
        return result;
    }

}
