package com.example.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;


public class MyPageMainLogic {

	public void mypage(Model model, JdbcTemplate jdbcTemplate, String loginId) {
		
		String myPageSQL = "SELECT * FROM postmsg WHERE loginId = '" + loginId + "' ORDER BY createdTime DESC";
		List<Map<String,Object>> myPageList = jdbcTemplate.queryForList(myPageSQL);
		model.addAttribute("myPageList", myPageList);
		
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		model.addAttribute("name", name);
		
		String createdTimeSQL = "SELECT lastlogin FROM user WHERE loginId = '" + loginId + "'";
		String createdTime = jdbcTemplate.queryForObject(createdTimeSQL, String.class);
		model.addAttribute("createdTime", createdTime);
	}
	
    public void changeNickName(Model model, JdbcTemplate jdbcTemplate, String loginId) {
		
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		model.addAttribute("name", name);

	}
    
    public String checkNickName(Model model, JdbcTemplate jdbcTemplate, String nickName) {
		
		String errMsg = "";
		if("".equals(nickName)) {
			errMsg = "※未入力です。";
			return errMsg;
		}
		if(nickName.length() > 30) {
			errMsg = "※30文字以内で入力してください";
			return errMsg;
		}

		return errMsg;
	}
    
    public void updateNickName(Model model, JdbcTemplate jdbcTemplate, String loginId, String nickName) {
		
		String nickNameSQL = "UPDATE user set name = '" + nickName + "' WHERE loginId = '" + loginId + "'"; 
		jdbcTemplate.update(nickNameSQL);
		

	}

}
