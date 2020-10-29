package com.xxk.management.test.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.roles.service.RolesService;
import com.xxk.management.test.service.TestService;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    private static Logger log = Logger.getLogger(TestController.class);

    @Autowired
    private TestService testService;


    @ResponseBody
    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public Object t1() {
        Map<String, Object> result = new HashMap<>();
        String v1 = null;
        try {
            v1 = testService.test1("");
            log.warn(v1);
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return v1;
    }

    @ResponseBody
    @RequestMapping(value = "/t2", method = RequestMethod.GET)
    public Object t2( @RequestParam(value = "sleep") String sleep) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (sleep.equals("on")) {
                Thread.currentThread().sleep(10000);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return sleep;
    }

}
