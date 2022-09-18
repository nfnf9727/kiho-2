package com.example.kiho.postresult;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import com.example.kiho.top.TopMainLogic;

public class PostDeleteMainLogic {
	public void postDeleteMainLogic(Model model,JdbcTemplate jdbcTemplate,int no) {
		System.out.println("postDeleteMainLogicメソッド開始");
//		投稿画面に表示しているpostmsgのnoを取得
		System.out.println(no);
//		画面表示している投稿内容の削除
		String sql1 = "DELETE FROM postmsg WHERE no = '" + no + "'";
		jdbcTemplate.update(sql1);
		
		// top画面を表示させるためにDB内容取得
//		TopMainLogic tc = new TopMainLogic();
//		tc.topHashTag(model, jdbcTemplate);
//		tc.topImagePath(model, jdbcTemplate);
//		tc.topNo(model, jdbcTemplate);
//		tc.topCategory(model, jdbcTemplate);
	}
}
