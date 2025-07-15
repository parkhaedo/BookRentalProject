package com.office.library.book.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.office.library.book.admin.util.UploadFileService;
import com.office.library.book.BookVo;

@Controller
@RequestMapping("/book/admin")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	//도서등록
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[Bookcontroller] registerBookForm()");
		String nextPage = "admin/book/register_book_form";
		return nextPage;
	}
	
	//도서등록확인
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo bookVo,
										@RequestParam("file") MultipartFile file) {
		System.out.println("[Bookcontriller] registerBookConfirm");
		String nextPage = "admin/book/register_book_ok";
		
		String savedFileName = uploadFileService.upload(file);
		
		if(savedFileName != null) {
			bookVo.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVo);
			
			if(result <=0)
				nextPage = "admin/book/register_book_ng";
		}else {
			nextPage = "admin/book/register_book_ng";
		}
		return nextPage;
	}
}
