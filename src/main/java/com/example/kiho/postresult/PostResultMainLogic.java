package com.example.kiho.postresult;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;


public class PostResultMainLogic {

	public void postresult(Model model, JdbcTemplate jdbcTemplate, String no) {
				
		String postTextSQL = "SELECT postText FROM postmsg WHERE no = '" + no + "'";
		String postText = jdbcTemplate.queryForObject(postTextSQL, String.class);
		model.addAttribute("postText", postText);
		
		String imageSQL = "SELECT image FROM postmsg WHERE no = '" + no + "'";
		String image = jdbcTemplate.queryForObject(imageSQL, String.class);
		model.addAttribute("image", image);
		
		//いいね数取ってくるのあとで
		
		//String commentsSQL = "SELECT * FROM comments WHERE loginId = '" + loginId + "' ORDER BY createdTime ASC";
		//List<Map<String,Object>> commentsList = jdbcTemplate.queryForList(commentsSQL);
		//model.addAttribute("commentsList", commentsList);
		System.out.println(postText);
		System.out.println(image);
		//System.out.println(postText);
	}

}