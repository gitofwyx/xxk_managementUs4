package com.xxk.management.user.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.roles.service.RolesService;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.log4j.Logger;
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
@RequestMapping("UserView")
public class UserViewController extends BaseController {

    private static Logger log = Logger.getLogger(UserViewController.class);

    @Autowired
    private RebUserService rebUserService;

    @Autowired
    private RolesService rolesService;

    @RequestMapping("/user-details")
    public ModelAndView  storage_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/detail/user-details", "result", result);
    }

}
