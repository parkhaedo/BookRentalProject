package com.office.library.book.admin;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.office.library.book.BookVo;

@Service
public class BookService {
	
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;
	final static public int BOOK_REGISTER_SUCCESS = 1;
	final static public int BOOK_REGISTER_FAIL = -1;

	@Autowired
	BookDao bookDao;
	
	public int registerBookconfirm(BookVo bookVo) {
		System.out.println("[BookService] registerBookConfirm()");
		
		boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
		
		if(!isISBN) {
			int result = bookDao.insertBook(bookVo);
			
			if(result>0)
				return BOOK_REGISTER_SUCCESS;
			else
				return BOOK_REGISTER_FAIL;
		}else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
}
