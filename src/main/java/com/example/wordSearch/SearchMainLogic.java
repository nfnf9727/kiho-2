package com.example.wordSearch;

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

public class SearchMainLogic {
	
	/**
	 * ワード検索
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public void wordSearch(Model model, JdbcTemplate jdbcTemplate, String wordText) {

		String noSQL = "SELECT * FROM postmsg WHERE postText LIKE '%" + wordText + "%' OR hashTag LIKE '%" + wordText +"%' ORDER BY createdTime DESC";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(noSQL);
		List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no", list.get(i).get("no"));
			map.put("hashTag", list.get(i).get("hashTag"));
			map.put("postText", list.get(i).get("postText"));
			map.put("iine", list.get(i).get("iine"));
			
			if ("".equals(list.get(i).get("image"))) {
				map.put("image", list.get(i).get("image"));
				postList.add(map);
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
					postList.add(map);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			}
		}
		//沖田追加↓
		model.addAttribute("midashimessage","検索結果");
		int hit = postList.size();
		model.addAttribute("searchmessage","「" + wordText  + "」で" + hit + "件の投稿がみつかりました");
		//沖田追加↑
		model.addAttribute("postList", postList);
	}
	
	/**
	 * カテゴリ検索
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public void categorySearch(Model model, JdbcTemplate jdbcTemplate, String category) {

		String noSQL = "SELECT * FROM postmsg WHERE hashTag = '" + category + "' ORDER BY createdTime DESC";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(noSQL);
		List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no", list.get(i).get("no"));
			map.put("hashTag", list.get(i).get("hashTag"));
			map.put("postText", list.get(i).get("postText"));
			map.put("iine", list.get(i).get("iine"));
			
			if ("".equals(list.get(i).get("image"))) {
				map.put("image", list.get(i).get("image"));
				postList.add(map);
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
					postList.add(map);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			}
		}
		//沖田追加↓
		model.addAttribute("midashimessage","検索結果");
		int hit = postList.size();
		model.addAttribute("searchmessage",category + "は" + hit + "件投稿されています");
		//沖田追加↑
		model.addAttribute("postList", postList);
	}
	
	/**
	 * 全ての投稿
	 * @param model
	 * @param jdbcTemplate
	 */
	public void allPost(Model model, JdbcTemplate jdbcTemplate) {

		String SQL = "SELECT * FROM postmsg ORDER BY createdTime DESC";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(SQL);
		List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no", list.get(i).get("no"));
			map.put("hashTag", list.get(i).get("hashTag"));
			map.put("postText", list.get(i).get("postText"));
			map.put("iine", list.get(i).get("iine"));
			
			if ("".equals(list.get(i).get("image"))) {
				map.put("image", list.get(i).get("image"));
				postList.add(map);
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
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				postList.add(map);
			}
		}
		System.out.println(postList);
		//沖田追加↓
		model.addAttribute("midashimessage","すべての投稿");
		//沖田追加↑
		model.addAttribute("postList", postList);
	}

}
