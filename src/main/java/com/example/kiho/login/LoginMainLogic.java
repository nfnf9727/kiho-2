package com.example.kiho.login;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;

public class LoginMainLogic {

	/**
	 * ログイン ユーザーＩＤチェック
	 * 
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public int uidCheck(Model model, JdbcTemplate jdbcTemplate, String uid, String password) {

		// エラーＳＷ変数定義
		int errsw1 = 0;

		// １：インプットチェック１（初期値チェック）

		// １－ａ：ユーザＩＤ未入力（初期値）チェック
		if (uid == "") {
			System.out.println("ユーザＩＤが未入力です");
			model.addAttribute("message1", "ユーザＩＤが未入力です");
			errsw1 = 1;
		}

		// １－ｂ：パスワード未入力（初期値）チェック

		if (password == "") {
			System.out.println("パスワードが未入力です");
			model.addAttribute("message2", "パスワードが未入力です");
			errsw1 = 1;
		}

		// ２：インプットチェック（userテーブル突き合わせチェック）

		if (errsw1 == 0) {

			String idCheckSQL = "SELECT COUNT(*) FROM user WHERE loginID = '" + uid + "'";
			String idCheck = jdbcTemplate.queryForObject(idCheckSQL, String.class);
			System.out.println(idCheck);

			if ("0".equals(idCheck)) {
				System.out.println("ユーザＩＤが一致しません");
				model.addAttribute("message1", "ユーザＩＤが一致しません");
				errsw1 = 1;
			}

			if (errsw1 == 0) {
				String loginIdSQL = "SELECT loginId FROM user WHERE loginID = '" + uid + "'";
				String loginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);

				// ２－ａ：ユーザＩＤ テーブルとの突き合わせチェック
				if (!uid.equals(loginId)) {
					System.out.println("ユーザＩＤが一致しません");
					model.addAttribute("message1", "ユーザＩＤが一致しません");
					errsw1 = 1;
				}

				String loginPasswordSQL = "SELECT password FROM user WHERE loginID = '" + uid + "'";
				String loginPassword = jdbcTemplate.queryForObject(loginPasswordSQL, String.class);

				// ２－ｂ：パスワード テーブルとの突き合わせチェック
				if (!password.equals(loginPassword)) {
					System.out.println("パスワードが一致しません");
					model.addAttribute("message2", "パスワードが一致しません");
					errsw1 = 1;
				}

			}
		}

		// ３：初期パスワードの強制変更

		if (errsw1 == 0) {

			if (password.equals("kihos")) {
				System.out.println("初期パスワードのため、パスワード変更を行ってください");
				model.addAttribute("message", "初期パスワードのため、パスワード変更を行ってください");
				model.addAttribute("loginId", uid);
				errsw1 = 2;
			}

		}

		return errsw1;

	}

}