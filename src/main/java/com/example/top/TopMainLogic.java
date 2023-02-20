package com.example.top;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

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


public class TopMainLogic {
	
	public void topHashTag(Model model,JdbcTemplate jdbcTemplate) {
		
		String hashTagSQL = "SELECT hashTag FROM postmsg ORDER BY createdTime DESC";
		List<String> hashTagList = jdbcTemplate.queryForList(hashTagSQL,String.class);	
		model.addAttribute("hashTagList", hashTagList);
		
	}
	
	public void topImagePath(Model model,JdbcTemplate jdbcTemplate) {
		
        String imageSQL = "SELECT image FROM postmsg ORDER BY createdTime DESC";
        List<String> imagePathSQLList = jdbcTemplate.queryForList(imageSQL,String.class);
    	List<String> imagePathList = new ArrayList<>();
    	for(int i = 0; i < 10 ; i++) {
//    		詳細不明だが、postmsgテーブルの状態によってindexoutofboundでエラーになるときがある。
    		if(imagePathSQLList.get(i) == null ||imagePathSQLList.get(i).isEmpty() || imagePathSQLList.get(i).isBlank()) {
    			imagePathList.add("");
    		}else {
    			AWSCredentials credentials = new BasicAWSCredentials("AKIA25JTTTFF4U75OIMU","u1iS2VMTsAZvIZtmPRvs1yVgjXxBLrWHg60GtvAr");
    		    // S3クライアントの生成
    		    AmazonS3 s3Client = AmazonS3ClientBuilder
    		            .standard()
    		            .withCredentials(new AWSStaticCredentialsProvider(credentials))
    		            .withRegion(Regions.AP_NORTHEAST_1)
    		            .build();
    		    // バケット名とS3のファイルパス（キー値）を指定
    		    GetObjectRequest request = new GetObjectRequest("bucket-5omsgu", imagePathSQLList.get(i));
    		    // ファイルダウンロード
    		    S3Object object = s3Client.getObject(request);
    		    String fileBase64 = null;

                // バイナリデータをBase64に変換
    	        byte[] encode;
				try {
					encode = Base64.getEncoder().encode(IOUtils.toByteArray(object.getObjectContent()));
					fileBase64 = new String(encode);
					String base64 = "data:image/png;base64," + fileBase64;
					imagePathList.add(base64);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
        		
    		}
    	}
        model.addAttribute("imagePathList", imagePathList);
		
	}
	
	public void topNo(Model model,JdbcTemplate jdbcTemplate) {
		
        String noSQL = "SELECT no FROM postmsg ORDER BY createdTime DESC";
        List<String> noList = jdbcTemplate.queryForList(noSQL,String.class);
        model.addAttribute("noList", noList);
	}
	
	
	public void topCategory(Model model,JdbcTemplate jdbcTemplate) {
		
        String categorySQL = "SELECT tag FROM tag";
        List<String> categoryList = jdbcTemplate.queryForList(categorySQL,String.class);
        model.addAttribute("categoryList", categoryList);
		
	}
	
	public void topName(Model model,JdbcTemplate jdbcTemplate) {
		
        String loginIdSQL = "SELECT loginId FROM postmsg";
        List<String> loginIdList = jdbcTemplate.queryForList(loginIdSQL,String.class);
        List<String> nameList = new ArrayList<>();
        for(int i = 0; i < loginIdList.size(); i++){
        	String nameSQL = "SELECT name FROM user WHERE loginID = '" + loginIdList.get(i) + "'";
        	String str = jdbcTemplate.queryForObject(nameSQL,String.class);
        	nameList.add(str);
        }
        model.addAttribute("nameList", nameList);
		
	}
	
	public String sessionCheck(HttpSession httpSession) {
		
		String flg = "0";
		String str = null;
		String loginId = (String) httpSession.getAttribute("loginId");
		if(str == loginId) {
			flg = "1";
		}
		
		return flg;
	}
	

}