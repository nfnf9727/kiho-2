package com.example.kiho.top;

import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


public class TopController {
	
	public List<String> topHashTag(Model model,JdbcTemplate jdbcTemplate) {
		
		String hashTagSQL = "SELECT hashTag FROM postmsg ORDER BY createdTime DESC";
		List<String> hashTagList = jdbcTemplate.queryForList(hashTagSQL,String.class);	
        
        return hashTagList;
		
	}
	
	public List<String> topImagePath(Model model,JdbcTemplate jdbcTemplate) {
		
        String imageSQL = "SELECT image FROM postmsg ORDER BY createdTime DESC";
        List<String> imagePathList = jdbcTemplate.queryForList(imageSQL,String.class);
        
        return imagePathList;
		
	}
	
	public List<String> topLoginId(Model model,JdbcTemplate jdbcTemplate) {
		
        String loginIdSQL = "SELECT loginId FROM postmsg ORDER BY createdTime DESC";
        List<String> loginIdList = jdbcTemplate.queryForList(loginIdSQL,String.class);
        
        return loginIdList;
		
	}

}
