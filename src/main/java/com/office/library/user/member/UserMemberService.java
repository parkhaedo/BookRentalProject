package com.office.library.user.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserMemberService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserMemberService.class);
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	UserMemberDao userMemberDao;
	
	public int createAccountConfirm(UserMemberVo userMemberVo) {
		logger.info("[UserMemberService] createAccountConfirm");
		
		boolean isMember = userMemberDao.isUserMember(userMemberVo.getU_m_id());
		
		if(!isMember) {
			int result = userMemberDao.insertUserAccount(userMemberVo);
			
			if(result > 0)
				return USER_ACCOUNT_CREATE_SUCCESS;
			else
				return USER_ACCOUNT_CREATE_FAIL;
		}else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}
	
	public UserMemberVo loginConfirm(UserMemberVo userMemberVo) {
		logger.info("[UserMemberService] loginConfirm()");
		UserMemberVo loginedUserMemberVo = userMemberDao.selectUser(userMemberVo);
		
		if(loginedUserMemberVo != null)
			logger.info("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		else
			logger.info("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		
		return loginedUserMemberVo;
		
	}
}
