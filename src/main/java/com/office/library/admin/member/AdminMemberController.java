package com.office.library.admin.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	//회원가입
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMembercontroller] createAccountForm()");
		String nextPage = "admin/member/create_account_form";
		return nextPage;
	}
	
	//회원가입 확인
	@RequestMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok";
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		String nextPage = "admin/member/login_form";
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		System.out.println(session.toString());
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		if(loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		}else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
	@RequestMapping(value="/logoutConfirm", method = RequestMethod.GET)
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		String nextPage = "redirect:/admin";
		session.invalidate();
		return nextPage;
	}
		
	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
	public ModelAndView listupAdmin() {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(nextPage);
		modelAndView.addObject("adminMemberVos", adminMemberVos);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/setAdminApproval", method=RequestMethod.GET)
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberVontroller] setAdminApproval()");
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
	
	@RequestMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountForm()");
		
		String nextPage = "admin/member/modify_account_form";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo)session.getAttribute("loginedAdminMemberVo");
		if(loginedAdminMemberVo == null)
			nextPage = "redirect:/admin/member/loginForm";
		
		return nextPage;
	}
	
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountconfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/modify_account_ok";
		
		int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
		
		if(result > 0) {
			AdminMemberVo loginedAdminMemberVo = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
			
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage = "admin/member/modify_account_ng";
		}
		
		return nextPage;
		
	}
	
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		System.out.println("[AdminMemberController] findPasswordForm()");
		String nextPage = "admin/member/find_password_form";
		return nextPage;
	}
	
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] findPasswordConfirm()");
		
		String nextPage = "admin/member/find_password_ok";
		
		int result = adminMemberService.findPasswordConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/find_password_ng";
		
		return nextPage;
	}
}
