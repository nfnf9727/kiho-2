package com.example.kiho.postMessage;


import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import com.example.kiho.top.TopController;

public class PostMessageController {
	
	public void postMessage(Model model,JdbcTemplate jdbcTemplate, PostForm form) {
		
		//項目チェック
		PostCheck pc = new PostCheck();
		if(pc.postCheck(form)) {
			//OK
			
		}else {
			//エラー
			
		}
		
		//現在時刻の設定
		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter dtformat = 
			DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fdate1 = dtformat.format(date1); 		
		//コメント
		//渋谷先生
		//ハッシュタグの設定
		String str = null;
		String hashTag = "";
		List<String> list = new ArrayList<>();
		list.add("#趣味");
		list.add("#休日");
		list.add("#ランチ");
		if(str == form.getHashtag() || form.getHashtag().isBlank() || form.getHashtag().isEmpty()) {
			if(form.getHashtagSelect().equals("2")) {
				hashTag = list.get(0);
			}else if(form.getHashtagSelect().equals("3")) {
				hashTag = list.get(1);
			}else {
				hashTag = list.get(2);
			}
		}else {
			hashTag = form.getHashtag();
		}
		
		
		//画像のファイル名生成　同じ画像をアップロードする可能性もあるため、被らないようランダムでファイル名を設定する
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
			Path path = Paths.get("src/main/resources/static/images/"+ sb + ".png");
			if(!form.getImage().isEmpty()) {
				byte[] bytes  = form.getImage().getBytes();
				OutputStream stream = Files.newOutputStream(path);
				stream.write(bytes);
			}

			if(form.getImage().isEmpty()) {
				String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime) VALUES('test1', '"+form.getPostText()+"',' "+hashTag+"', '','"+fdate1+"')";
				jdbcTemplate.update(sql1);	
			}else {
				String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime) VALUES('test1', '"+form.getPostText()+"', '"+hashTag+"', '"+path+"', '"+fdate1+"')";
				jdbcTemplate.update(sql1);	
			}
			
			//top画面を表示させるためにDB内容取得
			TopController tc = new TopController();
			tc.topHashTag(model, jdbcTemplate);
			tc.topImagePath(model, jdbcTemplate);
			tc.topLoginId(model, jdbcTemplate);

		}catch(Exception e){
    		e.printStackTrace();
    	}
	}

}
