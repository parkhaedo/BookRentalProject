package com.office.library.book.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;
import com.office.library.user.member.UserMemberVo;

import java.util.List;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book/user")
public class BookController {
	
	private final static Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	BookService bookService;
	
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVo bookVo, Model model) {
		logger.info("[UserBookController] searchBookConfirm()");
		
		String nextPage = "user/book/search_book";
		
		List<BookVo> bookVos = bookService.searchBookConfirm(bookVo);
		
		model.addAttribute("bookVos", bookVos);
		
		return nextPage;
	}
	
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		logger.info("[UserBookController] bookDetail()");
		
		String nextPage = "user/book/book_detail";
		
		BookVo bookVo = bookService.bookDetail(b_no);
		
		model.addAttribute("bookVo", bookVo);
		
		return nextPage;
	}
	
	@GetMapping("/rentalBookConfirm")
	public String rentalBookConfirm(@RequestParam("b_no") int b_no, HttpSession session) {
		logger.info("[UserBookController] rentalBookConfirm");
		String nextPage = "user/book/rental_book_ok";
		
		UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
		
		//if(loginedUserMemberVo == null)
			//return "redirect:/user/member/loginForm";
		
		int result = bookService.rentalBookConfirm(b_no, loginedUserMemberVo.getU_m_no());
		
		if(result <= 0)
			nextPage = "user/book/rental_book_ng";
		
		return nextPage;
	}
	
	@GetMapping("/enterBookshelf")
	public String enterBookshelf(HttpSession session, Model model) {
		logger.info("[UserBookController] enterBookshelf()");
		String nextPage = "user/book/bookshelf";
		UserMemberVo loginedUserMemberVo = (UserMemberVo)session.getAttribute("loginedUserMemberVo");
		List<RentalBookVo> rentalBookVos = bookService.enterBookshelf(loginedUserMemberVo.getU_m_no());
		
		model.addAttribute("rentalBookVos", rentalBookVos);
		
		return nextPage;
	}
	
	@GetMapping("listupRentalBookHistory")
	public String listupRentalBookHistory(HttpSession session, Model model) {
		logger.info("[UserBookController] listupRentalBookHistory()");
		
		String nextPage = "user/book/rental_book_history";
		UserMemberVo loginedUserMemberVo = (UserMemberVo)session.getAttribute("loginedUserMemberVo");
		
		List<RentalBookVo> rentalBookvos = bookService.listupRentalBookHistory(loginedUserMemberVo.getU_m_no());
		
		model.addAttribute("rentalBookVos", rentalBookvos);
		
		return nextPage;
	}
	
	@GetMapping("/requestHopeBookForm")
	public String requestHopeBookForm() {
		logger.info("[UserBookcontroller] requestHopeBookForm()");
		String nextPage = "user/book/request_hope_book_form";
		return nextPage;
	}
	
	@GetMapping("/requestHopeBookConfirm")
	public String requestHopeBookConfirm(HopeBookVo hopeBookVo, HttpSession session) {
		logger.info("[UserBookController] requestHopeBookConfirm()");
		String nextPage = "user/book/request_hope_book_ok";
		
		UserMemberVo loginedUserMemberVo = (UserMemberVo)session.getAttribute("loginedUserMemberVo");
		hopeBookVo.setU_m_no(loginedUserMemberVo.getU_m_no());
		
		int result = bookService.requestHopebookConfirm(hopeBookVo);
		
		if(result <= 0)
			nextPage = "user/book/request_hope_book_ng";
		
		return nextPage;
	}
	
	@GetMapping("/listupRequestHopeBook")
	public String listupRequestHopeBook(HttpSession session, Model model) {
		logger.info("[UserBookController] listupRequestHopeBook");
		String nextPage = "user/book/list_hope_book";
		UserMemberVo loginedUserMemberVo = (UserMemberVo)session.getAttribute("loginedUserMemberVo");
		List<HopeBookVo> hopeBookVos = bookService.listupRequestHopeBook(loginedUserMemberVo.getU_m_no());
		
		model.addAttribute("hopeBookVos", hopeBookVos);
		
		return nextPage;
	}
}
