package com.office.library.user.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class UserMemberDao {

	private Logger logger = LoggerFactory.getLogger(UserMemberDao.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isUserMember(String u_m_id) {
		logger.info("[UserMemberDao] isUserMember()");
		String sql = "SELECT COUNT(*) FROM tbl_user_member "
					+ "WHERE u_m_id = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, u_m_id);
		return result > 0 ? true : false;
	}
	
	public int insertUserAccount(UserMemberVo userMemberVo) {
		logger.info("[UserMemberDao] insertUserAccount()");
		String sql = "INSERT INTO tbl_user_member(u_m_id, "
					+"u_m_pw, " + "u_m_name, "+ "u_m_gender, "
					+"u_m_mail, "+"u_m_phone, "+ "u_m_reg_date, "
					+"u_m_mod_date) VALUES(?, ?, ?, ?, ?, ?, NOW(), NOW())";
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, userMemberVo.getU_m_id(),
										passwordEncoder.encode(userMemberVo.getU_m_pw()),
										userMemberVo.getU_m_name(), userMemberVo.getU_m_gender(),
										userMemberVo.getU_m_mail(), userMemberVo.getU_m_phone());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public UserMemberVo selectUser(UserMemberVo userMemberVo) {
		logger.info("[UserMemberDao] selectUser()");
		
		String sql = "SELECT * FROM tbl_user_member "
					+"WHERE u_m_id = ?";
		
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();
		
		try {
			logger.info("[UserMemberDao] userMemberVos"+userMemberVo.getU_m_id());
			userMemberVos = jdbcTemplate.query(sql, userRowMapper(), userMemberVo.getU_m_id());
			logger.info("[UserMemberDao] userMemberVos"+userMemberVos.size());
			if(!passwordEncoder.matches(userMemberVo.getU_m_pw(), userMemberVos.get(0).getU_m_pw()))
				userMemberVos.clear();
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("[UserMemberDao] selectUser()"+userMemberVos.size());
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
	}
	
	private RowMapper<UserMemberVo> userRowMapper(){
		
		return new RowMapper<UserMemberVo>() {
			
			@Override
			public UserMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException{
				UserMemberVo userMemberVo = new UserMemberVo();
				userMemberVo.setU_m_no(rs.getInt("u_m_no"));
				userMemberVo.setU_m_id(rs.getString("u_m_id"));
				userMemberVo.setU_m_pw(rs.getString("u_m_pw"));
				userMemberVo.setU_m_name(rs.getString("u_m_name"));
				userMemberVo.setU_m_gender(rs.getString("u_m_gender"));
				userMemberVo.setU_m_mail(rs.getString("u_m_mail"));
				userMemberVo.setU_m_phone(rs.getString("u_m_phone"));
				userMemberVo.setU_m_reg_date(rs.getString("u_m_reg_date"));
				userMemberVo.setU_m_mod_date(rs.getString("u_m_mod_date"));
				
				return userMemberVo;
			}		
		};
	}
	
	public int updateUserAccount(UserMemberVo userMemberVo) {
		logger.info("[UserMemberDao] updateUserAccount()");
		
		String sql = "UPDATE tbl_user_member SET "
				   + "u_m_name = ?, "+ "u_m_gender = ?, "+"u_m_mail = ?, "
				   + "u_m_phone = ?, "+"u_m_mod_date = NOW() "
				   + "WHERE u_m_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, userMemberVo.getU_m_name(), userMemberVo.getU_m_gender(),
										userMemberVo.getU_m_mail(), userMemberVo.getU_m_phone(),
										userMemberVo.getU_m_no());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public UserMemberVo selectUser(int u_m_no) {
		logger.info("[UserMemberDao] selectUser()");
		String sql = "SELECT * FROM tbl_user_member "
				   + "WHERE u_m_no = ?";
		
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();
		
		userMemberVos = jdbcTemplate.query(sql, userRowMapper(), u_m_no);
		
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
	}
	
	public UserMemberVo selectUser(String u_m_id, String u_m_name, String u_m_mail) {
		logger.info("[UserMemberDao] selectUser()");
		
		String sql = "SELECT * FROM tbl_user_member "
				   + "WHERE u_m_id = ? AND u_m_name = ? AND u_m_mail = ?";
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();
		
		userMemberVos = jdbcTemplate.query(sql, userRowMapper(), u_m_id, u_m_name, u_m_mail);
		
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
	}
	
	public int updatePassword(String u_m_id, String newPassword) {
		logger.info("[UserMemberDao] updatePassword()");
		
		String sql = "UPDATE tbl_user_member SET "
				   + "u_m_pw = ?, u_m_mod_date = NOW() "
				   + "WHERE u_m_id = ?";
		
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, passwordEncoder.encode(newPassword), u_m_id);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}