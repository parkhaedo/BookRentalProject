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
	
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		logger.info("[UserMemberController] modifyAccountForm()");
		String nextPage = "user/member/modify_account_form";
		UserMemberVo loginedUserMemberVo = (UserMemberVo)session.getAttribute("loginedUserMemberVo");
		if(loginedUserMemberVo == null)
			nextPage = "redirect:/user/member/loginForm";
		
		return nextPage;
	}
	
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {
		logger.info("[UserMemberController] modifyAccountConfirm");
		
		String nextPage = "user/member/modify_account_ok";
		
		int result = userMemberService.modifyAccountConfirm(userMemberVo);
		
		if(result > 0) {
			UserMemberVo loginedUserMemberVo = userMemberService.getLoginedUserMemberVo(userMemberVo.getU_m_no());
		
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60*30);
		}else {
			nextPage = "user/member/modify_account_ng";
		}
		return nextPage;
	}
	
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		logger.info("[UserMemberController] logoutConfirm");
		
		String nextPage = "redirect:/";
		session.invalidate();
		return nextPage;
	}
	
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		logger.info("[UserMembercontroller] findPasswordForm()");
		
		String nextPage = "user/member/find_password_form";
		
		return nextPage;
	}
	
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVo userMemberVo) {
		logger.info("[UserMemberVontroller] findPasswordConfirm()");
		String nextPage = "user/member/find_password_ok";
		
		int result = userMemberService.findPasswordConfirm(userMemberVo);
		if(result <= 0)
			nextPage = "user/member/find_password_ng";
		
		return nextPage;
	}
	
	
}
