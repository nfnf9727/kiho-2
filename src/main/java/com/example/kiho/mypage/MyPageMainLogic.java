package com.example.kiho.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;


public class MyPageMainLogic {

	public void mypage(Model model, JdbcTemplate jdbcTemplate, String loginId) {
		
		String myPageSQL = "SELECT * FROM postmsg WHERE loginId = '" + loginId + "' ORDER BY createdTime DESC";
		List<Map<String,Object>> myPageList = jdbcTemplate.queryForList(myPageSQL);
		model.addAttribute("myPageList", myPageList);
	}

}
