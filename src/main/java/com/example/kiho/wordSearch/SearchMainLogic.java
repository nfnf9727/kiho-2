package com.example.kiho.wordSearch;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;


public class SearchMainLogic {
	
	public void wordSearch(Model model,JdbcTemplate jdbcTemplate, String wordText) {
		
		String wordTextSQL = "SELECT no FROM postmsg WHERE postText LIKE '%"+ wordText +"%'";
		List<String> noList = jdbcTemplate.queryForList(wordTextSQL,String.class);	
		System.out.println(noList);
		
	}
	

}
