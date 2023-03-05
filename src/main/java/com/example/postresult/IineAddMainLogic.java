package com.example.postresult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.JdbcTemplate;

public class IineAddMainLogic {
	public void iineAddMainLogic(JdbcTemplate jdbcTemplate, int no, String loginId) {
		System.out.println("iineAddMainLogicメソッド開始");
		System.out.println(no);
		//postmsgテーブルのいいね！数を更新
		String iineSql = "SELECT iine FROM postmsg WHERE no = '" + no + "'";
		String iine = jdbcTemplate.queryForObject(iineSql, String.class);
		int iineCount = Integer.parseInt(iine) + 1;
		String sql = "UPDATE postmsg set iine = '" + iineCount + "' WHERE no = '" + no + "'"; 
		jdbcTemplate.update(sql);
		
		// 通知機能追加
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fdate1 = dtformat.format(date1);
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE no = '" + no + "'";
		String receiveLoginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);
		String postTextSQL = "SELECT postText FROM postmsg WHERE no = '" + no + "'";
		String postText = jdbcTemplate.queryForObject(postTextSQL, String.class);String text = "";
		if(10 > postText.length()) {
			text = postText;
		}else {
			text = postText.substring(0, 10);
		}
		String notification = name + " さんが、" + text + "… の投稿にいいねしました！";
		String sql1 = "INSERT INTO notifications(postLoginId, receiveLoginId, postNo, notification, time) VALUES('" + loginId + "', '" + receiveLoginId + "', '"
				+ no + "','" + notification + "', '" + fdate1 + "')";
		jdbcTemplate.update(sql1);
		
	}

}
