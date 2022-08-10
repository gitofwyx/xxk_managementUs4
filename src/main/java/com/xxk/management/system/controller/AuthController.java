package com.xxk.management.system.controller;

import com.xxk.core.file.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
public class AuthController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @RequestMapping("/")
    public ModelAndView  indexLogin() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }

    @RequestMapping("/main")
    public ModelAndView main() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/main", "result", result);
    }

    @RequestMapping(value = "/login")//用户登录路径
    public ModelAndView  login(HttpServletRequest request) {
        // shiro在认证过程中出现错误后将异常类路径通过request返回
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        UsernamePasswordToken token = null;
        Map<String, String> result = new HashMap<>();
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        if (!currentUser.isAuthenticated()) {
            String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
           if (!"".equals(username) && username != null) {
                token = new UsernamePasswordToken(username, password);// 获取登录令牌
                try {
                    //token.setRememberMe(true);
                    currentUser.login(token);
                    result.put("success", "已登录");
                    SavedRequest saveRequest = WebUtils.getSavedRequest(request);
                    /*if(!"".equals(saveRequest)&&saveRequest!=null){
                        return saveRequest.getRequestURI();
                    }else {
                        return "/main";
                    }*/
                    return new ModelAndView("/reg", "result", result);
                } catch (UnknownAccountException uae) {
                    result.put("error", "帐号未注册");
                } catch (IncorrectCredentialsException ice) {
                    result.put("error", "用户名/密码错误");
                } catch (ExcessiveAttemptsException eae) {
                    // 捕获错误登录过多的异常
                    result.put("error", "登录超次");
                } catch (AuthenticationException ae) {
                    result.put("error", "系统错误");
                }
            }
        } else {
            result.put("warnings", ",重新登录请先注销");
            return new ModelAndView("/form/bootstrap_form", "result", result);
        }
        return new ModelAndView("/index", "result", result);
    }

    @ResponseBody
    @RequestMapping("/unAuthorized")
    public Map<String, Object> unAuthorized() {
        Map<String, Object> result = new HashMap<>();
        log.warn("没有权限！");
        result.put("hasError", true);
        result.put("error", "没有权限！");
        return result;
    }
}
