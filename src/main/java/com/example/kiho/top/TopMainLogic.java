package com.example.kiho.top;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
        		imagePathList.add(imagePathSQLList.get(i));
    		}
    	}
        model.addAttribute("imagePathList", imagePathList);
		
	}
	
	public void topNo(Model model,JdbcTemplate jdbcTemplate) {
		
        String noSQL = "SELECT no FROM postmsg ORDER BY createdTime DESC";
        List<String> noList = jdbcTemplate.queryForList(noSQL,String.class);
        model.addAttribute("noList", noList);
	}
	
	
	public void topCategory(Model model,JdbcTemplate jdbcTemplate) {
		
        String categorySQL = "SELECT tag FROM tag";
        List<String> categoryList = jdbcTemplate.queryForList(categorySQL,String.class);
        model.addAttribute("categoryList", categoryList);
		
	}
	
	public void topName(Model model,JdbcTemplate jdbcTemplate) {
		
        String loginIdSQL = "SELECT loginId FROM postmsg";
        List<String> loginIdList = jdbcTemplate.queryForList(loginIdSQL,String.class);
        List<String> nameList = new ArrayList<>();
        for(int i = 0; i < loginIdList.size(); i++){
        	String nameSQL = "SELECT name FROM user WHERE loginID = '" + loginIdList.get(i) + "'";
        	String str = jdbcTemplate.queryForObject(nameSQL,String.class);
        	nameList.add(str);
        }
        model.addAttribute("nameList", nameList);
		
	}
	
	public String sessionCheck(HttpSession httpSession) {
		
		String flg = "0";
		String str = null;
		String loginId = (String) httpSession.getAttribute("loginId");
		if(str == loginId) {
			flg = "1";
		}
		
		return flg;
	}
	

}
