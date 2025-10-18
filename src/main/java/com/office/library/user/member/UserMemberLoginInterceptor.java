package com.office.library.user.member;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMemberLoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMemberLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							Object handler) throws Exception{
		logger.info("[UserMemberLoginInterceptor] preHandle()");
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object object = session.getAttribute("loginedUserMemberVo");
			if(object != null)
				return true;
		}
		response.sendRedirect(request.getContextPath()+"/user/member/loginForm");
		return false;
	}
}
