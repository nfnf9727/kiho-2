package com.example.postMessage;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.top.TopMainLogic;

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

//			// 画像のファイル名生成 同じ画像をアップロードする可能性もあるため、被らないようランダムでファイル名を設定する
//			StringBuilder sb1 = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
//			StringBuilder sb2 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//			StringBuilder sb3 = new StringBuilder("0123456789");
//			sb1.append(sb2);
//			sb1.append(sb3);
//			StringBuilder sb = new StringBuilder();
//			Random rand = new Random();
//			for (int i = 0; i < 20; i++) {
//				int num = rand.nextInt(sb1.length());
//				sb.append(sb1.charAt(num));
//			}
			try {
//				Path path = Paths.get("src/main/resources/static/images/" + sb + ".png");
//				if (!form.getImage().isEmpty()) {
//					byte[] bytes = form.getImage().getBytes();
//					OutputStream stream = Files.newOutputStream(path);
//					stream.write(bytes);
//				}
				
				if (form.getImage().isEmpty()) {
					String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime, iine) VALUES('" + loginId + "', '"
							+ form.getPostText() + "','" + hashTag + "', '','" + fdate1 + "', '0')";
					jdbcTemplate.update(sql1);
				} else {
					var filenameExtension = StringUtils.getFilenameExtension(form.getImage().getOriginalFilename());
				    var key = UUID.randomUUID().toString() + "." + filenameExtension;
				    System.out.println("key" + key);
				    var metadata = new ObjectMetadata();
				    metadata.setContentLength(form.getImage().getSize());
				    metadata.setContentType(form.getImage().getContentType());
					// S3へ画像をアップロード
					AWSCredentials credentials = new BasicAWSCredentials("AKIA25JTTTFF4U75OIMU","u1iS2VMTsAZvIZtmPRvs1yVgjXxBLrWHg60GtvAr");
				    // S3クライアントの生成
				    AmazonS3 s3Client = AmazonS3ClientBuilder
				            .standard()
				            .withCredentials(new AWSStaticCredentialsProvider(credentials))
				            .withRegion(Regions.AP_NORTHEAST_1)
				            .build();
					
				    s3Client.putObject("bucket-5omsgu", key, form.getImage().getInputStream(), metadata);
				    
					String sql1 = "INSERT INTO postmsg(loginId, postText, hashTag, image, createdTime, iine) VALUES('" + loginId + "', '"
							+ form.getPostText() + "', '" + hashTag + "', '" + key + "', '" + fdate1 + "', '0')";
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
