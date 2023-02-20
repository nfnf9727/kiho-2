package com.example.mypage;

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

public class MyPageMainLogic {

	public void mypage(Model model, JdbcTemplate jdbcTemplate, String loginId) {

		String myPageSQL = "SELECT * FROM postmsg WHERE loginId = '" + loginId + "' ORDER BY createdTime DESC";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(myPageSQL);
		List<Map<String, Object>> myPageList = new ArrayList<Map<String, Object>>();
		System.out.println("List:"+list);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no", list.get(i).get("no"));
			map.put("hashTag", list.get(i).get("hashTag"));
			map.put("postText", list.get(i).get("postText"));
			
			if ("".equals(list.get(i).get("image"))) {
				map.put("image", list.get(i).get("image"));
				System.out.println(map);
				myPageList.add(map);
			} else {
				AWSCredentials credentials = new BasicAWSCredentials("AKIA25JTTTFF4U75OIMU",
						"u1iS2VMTsAZvIZtmPRvs1yVgjXxBLrWHg60GtvAr");
				// S3クライアントの生成
				AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.withRegion(Regions.AP_NORTHEAST_1).build();
				// バケット名とS3のファイルパス（キー値）を指定
				String str = list.get(i).get("image").toString();
				GetObjectRequest request = new GetObjectRequest("bucket-5omsgu", str);
				// ファイルダウンロード
				S3Object object = s3Client.getObject(request);
				String fileBase64 = null;

				// バイナリデータをBase64に変換
				byte[] encode;
				try {
					encode = Base64.getEncoder().encode(IOUtils.toByteArray(object.getObjectContent()));
					fileBase64 = new String(encode);
					String base64 = "data:image/png;base64," + fileBase64;
					
					map.put("image", base64);
					myPageList.add(map);
					System.out.println(map);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			}
		}
	model.addAttribute("myPageList",myPageList);

	String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
	String name = jdbcTemplate.queryForObject(nameSQL, String.class);model.addAttribute("name",name);

	String createdTimeSQL = "SELECT lastlogin FROM user WHERE loginId = '" + loginId + "'";
	String createdTime = jdbcTemplate.queryForObject(createdTimeSQL,
			String.class);model.addAttribute("createdTime",createdTime);
	}

	public void changeNickName(Model model, JdbcTemplate jdbcTemplate, String loginId) {

		String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginId + "'";
		String name = jdbcTemplate.queryForObject(nameSQL, String.class);
		model.addAttribute("name", name);

	}

	public String checkNickName(Model model, JdbcTemplate jdbcTemplate, String nickName) {

		String errMsg = "";
		if ("".equals(nickName)) {
			errMsg = "※未入力です。";
			return errMsg;
		}
		if (nickName.length() > 30) {
			errMsg = "※30文字以内で入力してください";
			return errMsg;
		}

		return errMsg;
	}

	public void updateNickName(Model model, JdbcTemplate jdbcTemplate, String loginId, String nickName) {

		String nickNameSQL = "UPDATE user set name = '" + nickName + "' WHERE loginId = '" + loginId + "'";
		jdbcTemplate.update(nickNameSQL);

	}

}
