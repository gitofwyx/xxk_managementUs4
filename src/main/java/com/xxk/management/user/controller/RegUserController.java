package com.xxk.management.user.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("")
public class RegUserController extends BaseController {

    private static Logger log = Logger.getLogger(RegUserController.class);

    @Autowired
    private RebUserService rebUserService;

    @ResponseBody
    @RequestMapping("/listRegUser")
    public  Map<String, Object> listRegUser(@RequestParam(value = "pageIndex")String pageIndex,
                                            @RequestParam(value = "limit")String limit,
                                            @RequestParam(value = "account")String account,
                                            @RequestParam(value = "name")String name,
                                            @RequestParam(value = "startDate")String startDate,
                                            @RequestParam(value = "startDate")String endDate) {
        Map<String, Object> result = new HashMap<>();
        try{
            int pageNumber =Integer.parseInt(pageIndex)  + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<RegUser> listRegUser=rebUserService.listRegUser(pageNumber,pageSize);
            if(listRegUser==null){
                log.error("获取分页出错");
                result.put("error",false);
                return result;
            }
            else{
                result.put("rows",listRegUser);
                result.put("results",7);
            }
        }catch (Exception e){
            log.error(e);
            result.put("error",false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addRegUser")
    public Map<String,Boolean> addRegUser(RegUser user) {
        Map<String, Boolean> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try {
            user.setId(id);
            user.setAccount(id);
            user.setPassword("123");
            user.setCreateDate(createDate);
            user.setCreateUserId(id);
            user.setUpdateDate(createDate);
            user.setUpdateUserId(id);
            user.setDeleteFlag("0");

            boolean Result = rebUserService.addRegUser(user);
            if (!(Result)) {
                result.put("error", false);
            } else {
                result.put("success",true);
            }
        } catch (Exception e) {
            result.put("error", false);
            log.info(e);
        }
        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/getRegUser")
    public Map<String, Object> getRegUser(String id) {
        Map<String, Object> result = new HashMap<>();
        try{
            RegUser user=rebUserService.getRegUser(id);
            if(user==null){
                log.error("获取出错");
                result.put("error",false);
                return result;
            }
            result.put("Object",user);
            result.put("success",true);
        }catch (Exception e){
            log.error(e);
            result.put("error",false);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/updateRegUser")
    public Map<String, Boolean> updateRegUser(RegUser user) {
        Map<String, Boolean> result = new HashMap<>();
        //String phone=(String)request.getAttribute("phone");
        //String phone = request.getParameter("phone");
        String updateDate = DateUtil.getFullTime();
        try{
            user.setUpdateUserId("admin");
            user.setUpdateDate(updateDate);
            if(rebUserService.updateRegUser(user)==false){
                log.error("更新出错");
                result.put("success",true);
                return result;
            }
            result.put("success",true);
        }catch (Exception e){
            log.error(e);
            result.put("error",false);
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/delRegUserOfLogic")
    public  Map<String, Boolean> delRegUserOfLogic(@RequestParam("ids[]") List<String> ids) {
        Map<String, Boolean> result = new HashMap<>();
        try{
            if(rebUserService.deleteListRegUser(ids)==false){
                log.error("删除出错");
                result.put("error",false);
                return result;
            }
            result.put("success",true);
        }catch (Exception e){
            log.error(e);
            result.put("error",false);
        }finally {
            return result;
        }
    }

    @RequestMapping("/page1")
    public String Page() {
        //转发方式1
        return "user/loginPage";
        //return "/system/registPage";
    }


}
