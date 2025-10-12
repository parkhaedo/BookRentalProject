package com.office.library.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserHomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserHomeController.class);
	
	@GetMapping({"", "/"})
	public String home() {
		logger.info("[UserHomeController] home()");
		String nextPage = "user/home";
		return nextPage;
	}
	
}
