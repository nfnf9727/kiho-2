package com.example.postresult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;


public class PostResultMainLogic {

	public void postresult(Model model, JdbcTemplate jdbcTemplate, String sessionloginId, String no) {

	  //no,セッションＩＤを渡す
			model.addAttribute("no", no);
			model.addAttribute("sessionloginId", sessionloginId);
		
	 //postmsgテーブルからの取得（これ、まとめて持ってくればよくない？と後から思ったけど、もういいや・・・。）
		//投稿者ＩＤの取得
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE no = '" + no + "'";
		String loginId = jdbcTemplate.queryForObject(loginIdSQL, String.class);
		model.addAttribute("loginId", loginId);
		
		//投稿内容の取得
		String postTextSQL = "SELECT postText FROM postmsg WHERE no = '" + no + "'";
		String postText = jdbcTemplate.queryForObject(postTextSQL, String.class);
		model.addAttribute("postText", postText);

		//ハッシュタグの取得
		String hashTagSQL = "SELECT hashTag FROM postmsg WHERE no = '" + no + "'";
		String hashTag = jdbcTemplate.queryForObject(hashTagSQL, String.class);
		model.addAttribute("hashTag", hashTag);
		
		//投稿画像の取得
		String imageSQL = "SELECT image FROM postmsg WHERE no = '" + no + "'";
		String imagePath = jdbcTemplate.queryForObject(imageSQL, String.class);
		String image = "";
		if(!"".equals(imagePath)) {
			AWSCredentials credentials = new BasicAWSCredentials("AKIA25JTTTFF4U75OIMU","u1iS2VMTsAZvIZtmPRvs1yVgjXxBLrWHg60GtvAr");
		    // S3クライアントの生成
		    AmazonS3 s3Client = AmazonS3ClientBuilder
		            .standard()
		            .withCredentials(new AWSStaticCredentialsProvider(credentials))
		            .withRegion(Regions.AP_NORTHEAST_1)
		            .build();
		    // バケット名とS3のファイルパス（キー値）を指定
		    GetObjectRequest request = new GetObjectRequest("bucket-5omsgu", imagePath);
		    // ファイルダウンロード
		    S3Object object = s3Client.getObject(request);
		    String fileBase64 = null;

            // バイナリデータをBase64に変換
	        byte[] encode;
			try {
				encode = Base64.getEncoder().encode(IOUtils.toByteArray(object.getObjectContent()));
				fileBase64 = new String(encode);
				image = "data:image/png;base64," + fileBase64;
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		model.addAttribute("image", image);
		
		//投稿日時の取得
		String createdTimeSQL = "SELECT createdTime FROM postmsg WHERE no = '" + no + "'";
		String createdTime = jdbcTemplate.queryForObject(createdTimeSQL, String.class);
		model.addAttribute("createdTime", createdTime);
		
		//いいね数の取得
		String iineSQL = "SELECT iine FROM postmsg WHERE no = '" + no + "'";
		String iine = jdbcTemplate.queryForObject(iineSQL, String.class);
		model.addAttribute("iine", iine);

	//commentテーブルの取得
		String commentSQL = "SELECT * FROM comment WHERE no = '" + no + "' ORDER BY commentTime ASC";
		List<Map<String,Object>> tableList = jdbcTemplate.queryForList(commentSQL);
		List<Map<String,Object>> commentList = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < tableList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String nameSQL = "SELECT name FROM user WHERE loginId = '" + tableList.get(i).get("commenter") + "'";
			String name = jdbcTemplate.queryForObject(nameSQL, String.class);
			map.put("commenter", name);
			Object comments = tableList.get(i).get("comments");
			map.put("comments", comments);
			commentList.add(map);
		}
		model.addAttribute("commentList", commentList);
		
	//userテーブルからの取得
		//投稿者名の取得
		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		model.addAttribute("name", name);
		
		//投稿者名の取得
		String loginNameSQL = "SELECT name FROM user WHERE loginId = '" + sessionloginId + "'";
		String loginName = jdbcTemplate.queryForObject(loginNameSQL, String.class);
		model.addAttribute("loginName", loginName);
		
	//実装確認用
		System.out.println(loginId);
		System.out.println(postText);
		System.out.println(hashTag);
		System.out.println(image);
		System.out.println(createdTime);
		System.out.println(iine);
		System.out.println(commentList); //これだと１つしか出せない？まぁいっか。
		System.out.println(name);
		System.out.println(no);
		System.out.println(sessionloginId);

		
	}

}