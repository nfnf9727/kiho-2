package com.example.login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

		// ２－ａ：userテーブル存在チェック

		if (errsw1 == 0) {

			String idCheckSQL = "SELECT COUNT(*) FROM user WHERE loginID = '" + uid + "'";
			String idCheck = jdbcTemplate.queryForObject(idCheckSQL, String.class);
			System.out.println(idCheck);

			if ("0".equals(idCheck)) {
				System.out.println("ユーザＩＤが一致しません");
				model.addAttribute("message1", "ユーザＩＤが一致しません");
				errsw1 = 1;

			}

		}

		if (errsw1 == 0) {

			// ２－ｂ：ユーザＩＤ テーブルとの突き合わせチェック

			String loginIdSQL = "SELECT loginId FROM user WHERE loginID = '" + uid + "'";
			String loginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);

			if (!uid.equals(loginId)) {
				System.out.println("ユーザＩＤが一致しません");
				model.addAttribute("message1", "ユーザＩＤが一致しません");
				errsw1 = 1;
			}

			// ２－ｃ：パスワード テーブルとの突き合わせチェック

			String loginPasswordSQL = "SELECT password FROM user WHERE loginID = '" + uid + "'";
			String loginPassword = jdbcTemplate.queryForObject(loginPasswordSQL, String.class);

			if (!password.equals(loginPassword)) {
				System.out.println("パスワードが一致しません");
				model.addAttribute("message2", "パスワードが一致しません");
				errsw1 = 1;
			}

		}

		return errsw1;
	}

	public int prePass(Model model, JdbcTemplate jdbcTemplate, String uid, String password) {

		// エラーＳＷ変数定義
		int errsw2 = 0;

		// ３：初期パスワードの強制変更

		if (password.equals("kihos")) {
			System.out.println("初期パスワードのため、パスワード変更を行ってください");
			model.addAttribute("message", "初期パスワードのため、パスワード変更を行ってください");
			model.addAttribute("loginId", uid);
			errsw2 = 1;
		}

		return errsw2;

	}

	public int uidCheck2(Model model, JdbcTemplate jdbcTemplate, String uid) {

		// エラーＳＷ変数定義
		int errsw3 = 0;

		// １：インプットチェック１（初期値チェック）

		// １－ａ：ユーザＩＤ未入力（初期値）チェック
		if (uid == "") {
			System.out.println("ユーザＩＤが未入力です");
			model.addAttribute("message1", "ユーザＩＤが未入力です");
			errsw3 = 1;
		}

		// ２：インプットチェック（userテーブル突き合わせチェック）

		// ２－ａ：userテーブル存在チェック

		if (errsw3 == 0) {

			String idCheckSQL = "SELECT COUNT(*) FROM user WHERE loginID = '" + uid + "'";
			String idCheck = jdbcTemplate.queryForObject(idCheckSQL, String.class);
			System.out.println(idCheck);

			if ("0".equals(idCheck)) {
				System.out.println("ユーザＩＤが一致しません");
				model.addAttribute("message1", "ユーザＩＤが一致しません");
				errsw3 = 1;

			}

		}

		if (errsw3 == 0) {

			// ２－ｂ：ユーザＩＤ テーブルとの突き合わせチェック

			String loginIdSQL = "SELECT loginId FROM user WHERE loginID = '" + uid + "'";
			String loginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);

			if (!uid.equals(loginId)) {
				System.out.println("ユーザＩＤが一致しません");
				model.addAttribute("message1", "ユーザＩＤが一致しません");
				errsw3 = 1;
			}

		}

		return errsw3;
	}

	public void LastLogin(Model model, JdbcTemplate jdbcTemplate, String uid) {

		// 現在時刻の設定
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fdate1 = dtformat.format(date1);
		
		// ログイン時刻の更新
		
		System.out.println(fdate1);
		String LoginTime = "UPDATE user SET lastlogin = '" + fdate1 + "' WHERE loginID = '" + uid + "'";
		jdbcTemplate.update(LoginTime);

	}

}