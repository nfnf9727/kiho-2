package com.example.kiho.top;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;


public class TopMainLogic {
	
	public void topHashTag(Model model,JdbcTemplate jdbcTemplate) {
		
		String hashTagSQL = "SELECT hashTag FROM postmsg ORDER BY createdTime DESC";
		List<String> hashTagList = jdbcTemplate.queryForList(hashTagSQL,String.class);	
		model.addAttribute("hashTagList", hashTagList);
		
	}
	
	public void topImagePath(Model model,JdbcTemplate jdbcTemplate) {
		
        String imageSQL = "SELECT image FROM postmsg ORDER BY createdTime DESC";
        List<String> imagePathSQLList = jdbcTemplate.queryForList(imageSQL,String.class);
    	List<String> imagePathList = new ArrayList<>();
    	for(int i = 0; i < 10 ; i++) {
    		if(imagePathSQLList.get(i) == null ||imagePathSQLList.get(i).isEmpty() || imagePathSQLList.get(i).isBlank()) {
    			imagePathList.add("");
    		}else {
    			String result = imagePathSQLList.get(i).substring(25);
        		imagePathList.add(result);
    		}
    	}
        model.addAttribute("imagePathList", imagePathList);
		
	}
	
	public void topLoginId(Model model,JdbcTemplate jdbcTemplate) {
		
        String noSQL = "SELECT no FROM postmsg ORDER BY createdTime DESC";
        List<String> noList = jdbcTemplate.queryForList(noSQL,String.class);
        model.addAttribute("noList", noList);
		
	}

}
