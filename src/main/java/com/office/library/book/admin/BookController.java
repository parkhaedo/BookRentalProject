package com.office.library.book.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/book/admin")
public class BookController {
	
	//도서등록
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[Bookcontroller] registerBookForm()");
		String nextPage = "admin/book/register_book_form";
		return nextPage;
	}
}
