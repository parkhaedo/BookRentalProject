package com.office.library.book.user;

import org.springframework.stereotype.Service;
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
		
//		return bookDao.selectBooksBySearch(bookVo);
		return null;
	}
}
