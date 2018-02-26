package com.houdask.site.auth.shiro.manager;

import com.houdask.site.auth.shiro.token.SysAuthToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 表单验证（包含验证码）过滤类
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		//屏蔽记住账号功能
		boolean rememberMe = isRememberMe(request);
		String host =  request.getRemoteHost();

		return new SysAuthToken(username,password,rememberMe);
	}



	/**
	 * 登录成功之后跳转URL
	 */
	@Override
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
//		if (IncorrectCredentialsException.class.getName().equals(className)
//				|| UnknownAccountException.class.getName().equals(className)){
//			message = "用户或密码错误, 请重试.";
//		}else if(Exceptions.isCausedBy(e, ServiceException.class)){
//			message = "用户或密码错误, 请重试.";
//		}else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
//			message = StringUtils.replace(e.getMessage(), "msg:", "");
//		}
//		else{
//			message = "系统出现点问题，请稍后再试！";
//			e.printStackTrace(); // 输出到控制台
//		}
//        request.setAttribute(getFailureKeyAttribute(), className);
//        request.setAttribute(getMessageParam(), message);
        return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {  

		HttpServletRequest req = WebUtils.toHttp(request);
		HttpServletResponse res = WebUtils.toHttp(response);

		String xmlHttpRequest = req.getHeader("X-Requested-With");

		Subject subject = getSubject(request, response);  
		if (subject.getPrincipal() == null) {  
			if (xmlHttpRequest != null) {  
				res.sendError(401);
				return false;  
			}  
		} else {  
			if (xmlHttpRequest != null) {  
				res.sendError(403);
				return false;  

			}  
		}  
		return super.onAccessDenied(request, response);
	}
}