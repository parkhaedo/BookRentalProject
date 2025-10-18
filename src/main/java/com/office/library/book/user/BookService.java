package com.office.library.book.user;

import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class BookService {

	private final static Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	BookDao bookDao;

	public List<BookVo> searchBookConfirm(BookVo bookVo){
		logger.info("[BookService] searchBookConfirm()");
		return bookDao.selectBooksBySearch(bookVo);
	}
	
	public BookVo bookDetail(int b_no) {
		logger.info("[BookService] bookDetail()");
		return bookDao.selectBook(b_no);
	}
	
	public int rentalBookConfirm(int b_no, int u_m_no) {
		logger.info("[BookService] bookDetail()");
		
		int result = bookDao.insertRentalBook(b_no, u_m_no);
		if(result >=0)
			bookDao.updateRentalBookAble(b_no);
		
		return result;
	}
	
	public List<RentalBookVo> enterBookshelf(int u_m_no){
		logger.info("[BookService] enterBookshelf()");
		
		return bookDao.selectRentalBooks(u_m_no);
	}
	
	public List<RentalBookVo> listupRentalBookHistory(int u_m_no){
		logger.info("[BookService] listupRentalBookHistory()");
		return bookDao.selectRentalBookHistory(u_m_no);
	}
	
	public int requestHopebookConfirm(HopeBookVo hopeBookVo) {
		logger.info("[BookService] requestHopeBookconfirm()");
		return bookDao.insertHopeBook(hopeBookVo);
	}
	
	public List<HopeBookVo> listupRequestHopeBook(int u_m_no){
		logger.info("[BookService] listupRequestHopeBook");
		return bookDao.selectRequestHopeBook(u_m_no);
	}
	

}
