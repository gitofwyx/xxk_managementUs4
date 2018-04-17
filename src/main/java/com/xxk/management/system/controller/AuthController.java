package com.xxk.management.system.controller;

import com.xxk.core.file.BaseController;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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

    private static Logger log = Logger.getLogger(AuthController.class);

    @RequestMapping("/main")
    public ModelAndView main() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/main", "result", result);
    }

    @RequestMapping(value = "/login")//用户登录路径
    public String login(HttpServletRequest request) {
        // shiro在认证过程中出现错误后将异常类路径通过request返回
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                System.out.println("账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                System.out.println("用户名/密码错误");
            } else if("randomCodeError".equals(exceptionClassName)){
                System.out.println("验证码错误");
            } else{
                System.out.println("未知错误");
            }
            return "redirect:/";
        }
        return "redirect:/main";
    }
}
