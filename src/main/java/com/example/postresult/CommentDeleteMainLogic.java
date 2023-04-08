//コメント削除 --2023.3.9 okita Add-
package com.example.postresult;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

public class CommentDeleteMainLogic {
	public void commentDeleteMainLogic(Model model, JdbcTemplate jdbcTemplate, int no) {
		System.out.println("commentDeleteMainLogicメソッド開始");
//選択したcommentのnoを取得
		System.out.println(no);
//画面表示しているコメントの削除
		String sql1 = "DELETE FROM comment WHERE commentNo = '" + no + "'";
		jdbcTemplate.update(sql1);
		System.out.println("コメント削除OK");

	}

	public void newCommentDeleteMainLogic(Model model, JdbcTemplate jdbcTemplate, String no, String comments) {
		System.out.println("newCommentDeleteMainLogicメソッド開始");		
		String sql1 = "DELETE FROM comment WHERE comments = '" + comments + "' AND no = '" + no + "'";
		System.out.println(sql1);
		jdbcTemplate.update(sql1);
	}

}
