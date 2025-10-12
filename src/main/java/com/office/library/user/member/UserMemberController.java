package com.office.library.user.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMemberController.class);
	
	@Autowired
	UserMemberService userMemberService;
	
	//会員登録
	@GetMapping("/createAccountForm")	
	public String createAccountForm() {
		logger.info("[UserMemberController] createAccountForm()");
		String nextPage = "user/member/create_account_form";
		return nextPage;
	}
	
	@PostMapping("createAccountConfirm")
	public String createAccountConfirm(UserMemberVo userMemberVo) {
		logger.info("[UserMemberController] createAccountConfirm");
		String nextPage = "user/member/create_account_ok";
		int result = userMemberService.createAccountConfirm(userMemberVo);
		if(result <= 0)
			nextPage = "user/member/create_account_ng";
		
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		logger.info("[UserMemberController] loginForm()");
		String nextPage = "user/member/login_form";
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) {
		logger.info("[UserMemberController] loginConfirm()");
		String nextPage = "user/member/login_ok";
		UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo);
		
		if(loginedUserMemberVo == null) {
			nextPage = "user/member/login_ng";
		}else {
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
}
