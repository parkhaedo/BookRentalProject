package com.office.library.admin.member;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class AdminMemberDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
}
