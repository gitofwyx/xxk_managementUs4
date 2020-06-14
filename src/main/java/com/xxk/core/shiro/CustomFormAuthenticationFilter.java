package com.xxk.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private static Logger log = Logger.getLogger(CustomFormAuthenticationFilter.class);

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
	 * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
	 * */

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里进行验证码的校验
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String TD = httpServletRequest.getParameter("TD");
		if ("logout".equals(TD)) {
			httpServletResponse.setContentType("text/html;charset=utf-8");
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json; charset=utf-8");
			PrintWriter writer = httpServletResponse.getWriter();
			Map<String, Object> result = new HashMap<>();
			result.put("hasError", true);
			result.put("error","登录过期，请重新登录");
			writer.write(JSON.toJSONString(result));
			return false;
		}
		return super.onAccessDenied(request, response);
	}


	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
	        throws Exception
	    {
	        issueSuccessRedirect(request, response);
	        return false;
	    }
	
}
