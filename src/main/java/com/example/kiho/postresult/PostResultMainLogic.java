package com.example.kiho.postresult;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;


public class PostResultMainLogic {

	public void postresult(Model model, JdbcTemplate jdbcTemplate, String no) {

	 //postmsgテーブルからの取得（これ、まとめて持ってくればよくない？と後から思ったけど、もういいや・・・。）
		//投稿者ＩＤの取得
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE no = '" + no + "'";
		String loginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);
		model.addAttribute("loginId", loginId);
		
		//投稿内容の取得
		String postTextSQL = "SELECT postText FROM postmsg WHERE no = '" + no + "'";
		String postText = jdbcTemplate.queryForObject(postTextSQL, String.class);
		model.addAttribute("postText", postText);
		
		//投稿画像の取得
		String imageSQL = "SELECT image FROM postmsg WHERE no = '" + no + "'";
		String image = jdbcTemplate.queryForObject(imageSQL, String.class);
		model.addAttribute("image", image);
		
		//投稿日時の取得
		String createdTimeSQL = "SELECT createdTime FROM postmsg WHERE no = '" + no + "'";
		String createdTime = jdbcTemplate.queryForObject(createdTimeSQL, String.class);
		model.addAttribute("createdTime", createdTime);
		
		//いいね数の取得
		String iineSQL = "SELECT iine FROM postmsg WHERE no = '" + no + "'";
		String iine = jdbcTemplate.queryForObject(iineSQL, String.class);
		model.addAttribute("iine", iine);

	//commentテーブルの取得
		String commentSQL = "SELECT * FROM comment WHERE no = '" + no + "' ORDER BY commentTime ASC";
		List<Map<String,Object>> commentList = jdbcTemplate.queryForList(commentSQL);
		model.addAttribute("commentList", commentList);
		
	//userテーブルからの取得
		//投稿者名の取得
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		model.addAttribute("name", name);	
		
	//実装確認用
		System.out.println(loginId);
		System.out.println(postText);
		System.out.println(image);
		System.out.println(createdTime);
		System.out.println(iine);
		System.out.println(commentList); //これだと１つしか出せない？まぁいっか。
		System.out.println(name);
	}

}