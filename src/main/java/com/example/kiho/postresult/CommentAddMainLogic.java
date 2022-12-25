package com.example.kiho.postresult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommentAddMainLogic {
	public void commentAddMainLogic(JdbcTemplate jdbcTemplate, int no, String commenter, String comments) {

		// 現在時刻の設定
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fdate1 = dtformat.format(date1);
		String sql1 = "INSERT INTO comment(no, commenter, commentTime, comments) VALUES('" + no + "', '" + commenter + "','" + fdate1 + "', '" + comments + "')";
		jdbcTemplate.update(sql1);

		
	}

}
