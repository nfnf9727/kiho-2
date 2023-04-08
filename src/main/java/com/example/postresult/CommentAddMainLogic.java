package com.example.postresult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

public class CommentAddMainLogic {
	public void commentAddMainLogic(JdbcTemplate jdbcTemplate, int no, String commenter, String comments, Model model) {

		// 現在時刻の設定
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fdate1 = dtformat.format(date1);
		String sql1 = "INSERT INTO comment(no, commenter, commentTime, comments) VALUES('" + no + "', '" + commenter + "','" + fdate1 + "', '" + comments + "')";
		jdbcTemplate.update(sql1);
		
		// 現在時刻の設定
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + commenter + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE no = '" + no + "'";
		String receiveLoginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);
		String postTextSQL = "SELECT postText FROM postmsg WHERE no = '" + no + "'";
		String postText = jdbcTemplate.queryForObject(postTextSQL, String.class);
		String text = "";
		if(10 > postText.length()) {
			text = postText;
		}else {
			text = postText.substring(0, 10);
		}
		String notification = name + " さんが、 " + text + "… の投稿にコメントしました！";
		String sql = "INSERT INTO notifications(postLoginId, receiveLoginId, postNo, notification, time) VALUES('" + commenter + "', '" + receiveLoginId + "', '"
				+ no + "','" + notification + "', '" + fdate1 + "')";
		jdbcTemplate.update(sql);

	}

}
