package com.example.kiho.postMessage;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import com.example.kiho.top.TopMainLogic;

public class PostMessageMainLogic {

	public void postMessage(Model model, JdbcTemplate jdbcTemplate, PostForm form, String loginId) {

		// 項目チェック（jsとサーブレットのダブルチェック）
		PostCheck pc = new PostCheck();
		String errorMsg = "";
		errorMsg = pc.postCheck(form);
		if ("".equals(errorMsg)) {

			// 現在時刻の設定
			LocalDateTime date1 = LocalDateTime.now();
			DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String fdate1 = dtformat.format(date1);

			// ハッシュタグの設定
			String hashTag = "";
			if(form.getHashtagSelect().equals("0")) {
				hashTag = form.getHashtag();
			}else {
				hashTag = form.getHashtagSelect();
			}

			// 画像のファイル名生成 同じ画像をアップロードする可能性もあるため、被らないようランダムでファイル名を設定する
			StringBuilder sb1 = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
			StringBuilder sb2 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			StringBuilder sb3 = new StringBuilder("0123456789");
			sb1.append(sb2);
			sb1.append(sb3);
			StringBuilder sb = new StringBuilder();
			Random rand = new Random();
			for (int i = 0; i < 20; i++) {
				int num = rand.nextInt(sb1.length());
				sb.append(sb1.charAt(num));
			}
			try {
				Path path = Paths.get("src/main/resources/static/images/" + sb + ".png");
				if (!form.getImage().isEmpty()) {
					byte[] bytes = form.getImage().getBytes();
					OutputStream stream = Files.newOutputStream(path);
					stream.write(bytes);
				}
				
				String imagePath = "/images/" + sb + ".png";
				if (form.getImage().isEmpty()) {
					String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime, iine) VALUES('" + loginId + "', '"
							+ form.getPostText() + "','" + hashTag + "', '','" + fdate1 + "', '0')";
					jdbcTemplate.update(sql1);
				} else {
					String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime, iine) VALUES('" + loginId + "', '"
							+ form.getPostText() + "', '" + hashTag + "', '" + imagePath + "', '" + fdate1 + "', '0')";
					jdbcTemplate.update(sql1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
		//エラーメッセージの設定
		model.addAttribute("errorMsg", errorMsg);
			
		}
		// top画面を表示させるためにDB内容取得
		TopMainLogic tc = new TopMainLogic();
		tc.topHashTag(model, jdbcTemplate);
		tc.topImagePath(model, jdbcTemplate);
		tc.topNo(model, jdbcTemplate);
		tc.topCategory(model, jdbcTemplate);
	}

}
