package com.office.library.book.admin;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.office.library.book.BookVo;
import com.office.library.book.RentalBookVo;

import java.util.List;

@Service
public class BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;
	final static public int BOOK_REGISTER_SUCCESS = 1;
	final static public int BOOK_REGISTER_FAIL = -1;

	@Autowired
	BookDao bookDao;
	
	public int registerBookConfirm(BookVo bookVo) {
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
	
	public List<BookVo> searchBookConfirm(BookVo bookVo){
		System.out.println("[BookService] searchBookConfirm");
		
		return bookDao.selectBookBySearch(bookVo);
	}
	
	public BookVo bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		
		return bookDao.selectBook(b_no);
	}
	
	public BookVo modifyBookForm(int b_no) {
		System.out.println("[BookService] modifyBookForm");
		return bookDao.selectBook(b_no);
	}
	
	public int modifyBookConfirm(BookVo bookVo) {
		System.out.println("[BookService] modifyBookConfirm()");
		return bookDao.updateBook(bookVo);
	}
	
	public int deleteBookConfirm(int b_no) {
		System.out.println("[BookService] deleteBookConfirm()");
		return bookDao.deleteBook(b_no);
	}
	
	public List<RentalBookVo> getRentalBooks(){
		logger.info("[BookService] getRentalBooks()");
		return bookDao.selectRentalBooks();
	}
	
	public int returnBookConfirm(int b_no, int rb_no) {
		logger.info("[BookService] returnBookConfirm");
		int result = bookDao.updateRentalBook(rb_no);
		
		if(result > 0)
			result = bookDao.updateBook(b_no);
		
		return result;
	}
	
}
