package com.example.kiho.postresult;

import org.springframework.jdbc.core.JdbcTemplate;

public class IineAddMainLogic {
	public void iineAddMainLogic(JdbcTemplate jdbcTemplate,int no) {
		System.out.println("iineAddMainLogicメソッド開始");
		System.out.println(no);
		//postmsgテーブルのいいね！数を更新
		String iineSql = "SELECT iine FROM postmsg WHERE no = '" + no + "'";
		String iine = jdbcTemplate.queryForObject(iineSql, String.class);
		int iineCount = Integer.parseInt(iine) + 1;
		String sql = "UPDATE postmsg set iine = '" + iineCount + "' WHERE no = '" + no + "'"; 
		jdbcTemplate.update(sql);
		
	}

}
