package com.example.kiho.pwchange;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;

public class PwchangeMainLogic {

	/**
	 * パスワード変更 ユーザーＩＤチェック
	 * 
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public int passCheck(Model model, JdbcTemplate jdbcTemplate, String loginId, String newpass, String newpass2) {

		// エラーＳＷ変数定義
		int errsw1 = 0;

		// １：インプットチェック１（初期値チェック）

		// １－ａ：新パスワード未入力（初期値）チェック
		if (newpass == "") {
			System.out.println("変更後のパスワードを入力してください");
			model.addAttribute("message1", "変更後のパスワードを入力してください");
			errsw1 = 1;
		}

		// １－ｂ：再入力パスワード未入力（初期値）チェック
		if (newpass2 == "") {
			System.out.println("変更後のパスワードを再入力してください");
			model.addAttribute("message2", "変更後のパスワードを再入力してください");
			errsw1 = 1;
		}

		// ２：インプットチェック２（入力値のチェック）

		if (errsw1 == 0) {

			// ２－ａ：新パスワードの再入力 突き合わせチェック ※２回入れたパスワードが同じであること
			if (!newpass.equals(newpass2)) {
				System.out.println("再入力したパスワードが不一致です。再度変更後のパスワードを入力してください");
				model.addAttribute("message1", "再入力したパスワードが不一致です。再度変更後のパスワードを入力してください");
				errsw1 = 1;
			}
		}

		if (errsw1 == 0) {

			// ２－ｂ：初期パスワードチェック ※新パスワード＝"kihos"はエラー
			if (newpass.equals("kihos")) {
				System.out.println("変更後パスワードが初期パスワードと一致しています。別のパスワードを設定してください。");
				model.addAttribute("message1", "変更後パスワードが初期パスワードと一致しています。別のパスワードを設定してください。");
				errsw1 = 1;
			}
		}

		if (errsw1 == 0) {
			
			String pwCheckSQL2 = "SELECT COUNT(*) FROM user WHERE loginID = '" + loginId + "'";
			String pwCheck2 = jdbcTemplate.queryForObject(pwCheckSQL2, String.class);
			System.out.println(pwCheck2);
			
			String pwCheckSQL = "SELECT password FROM user WHERE loginID = '" + loginId + "'";
			String pwCheck = jdbcTemplate.queryForObject(pwCheckSQL, String.class);
			System.out.println(pwCheck);

			// ２－ｂ：新パスワード テーブルとの突き合わせチェック ※新旧一致していたらエラー
			if (newpass.equals(pwCheck)) {
				System.out.println("変更後のパスワードが旧パスワードと一致しています。");
				model.addAttribute("message1", "変更後のパスワードが旧パスワードと一致しています。");
				errsw1 = 1;

			}
		}
		return errsw1;
	}
}
