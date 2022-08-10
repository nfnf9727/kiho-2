package com.example.kiho;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.kiho.postMessage.PostForm;
import com.example.kiho.postMessage.PostMessageController;
import com.example.kiho.top.TopController;


@Controller
public class MainController {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	// テスト用です。このメソッド後で消します。
	// 本来はshowIndex()を経由してトップ画面を表示します
	// postmsgテーブル（投稿内容を格納するテーブル）未作成であれば情報取れずエラーになりますので、postmsgテーブルを作成してください
	// 投稿する場合も、postmsgテーブルを作成してから入力ください
	// 接続URL：http://localhost:8080/
    @RequestMapping(path = "/")
    public String test(Model model) {
    	
    	//top画面を表示させるために無理やり値入れてます
    	List<String> imagePathList = new ArrayList<>();
    	List<String> loginIdList = new ArrayList<>();
    	List<String> hashTagList = new ArrayList<>();
    	imagePathList.add("/images/WeX3PTyUSzmPEEuleRX2.png");
    	imagePathList.add("/images/iVKbmeqLt7uB1tRBEnNB.png");
    	imagePathList.add("");
    	imagePathList.add("");
    	imagePathList.add("/images/IvdDeloUfZYgqj0YTL1H.png");
    	imagePathList.add("");
    	imagePathList.add("");
    	imagePathList.add("/images/iCsHDGylZU56yfEH83y5.png");
    	imagePathList.add("");
    	imagePathList.add("/images/HRtFLCxcBbpOiujcMSIc.png");
    	
    	loginIdList.add("test1");
    	loginIdList.add("test2");
    	loginIdList.add("test3");
    	loginIdList.add("test4");
    	loginIdList.add("test5");
    	loginIdList.add("test6");
    	loginIdList.add("test7");
    	loginIdList.add("test8");
    	loginIdList.add("test9");
    	loginIdList.add("test10");
    	
    	hashTagList.add("#ハッシュタグ");
    	hashTagList.add("#今日の１枚");
    	hashTagList.add("#今日のご飯");
    	hashTagList.add("#ランチ");
    	hashTagList.add("#夜食");
    	hashTagList.add("#おにぎり");
    	hashTagList.add("#おすすめ");
    	hashTagList.add("#書くことなくなってきたよ");
    	hashTagList.add("#なににしよーかなー");
    	hashTagList.add("#ラスト");
    	
    	model.addAttribute("hashTagList", hashTagList);
    	model.addAttribute("imagePathList", imagePathList);
    	model.addAttribute("loginIdList", loginIdList);
    	model.addAttribute("userName", "しぶたに");
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
    	
    	return "top";
    }
	
    // 本来はこっち(top画面を表示するメソッド)    
    // postmsgテーブル作成後、接続してください
    // 接続URL：http://localhost:8080/top
    @RequestMapping(path = "/top")
    public String showIndex(Model model) {

    	TopController tc = new TopController();
    	List<String> hashTagList = tc.topHashTag(model, jdbcTemplate);
    	List<String> imageList = tc.topImagePath(model, jdbcTemplate);
    	List<String> loginIdList = tc.topLoginId(model, jdbcTemplate);
    	List<String> imagePathList = new ArrayList<>();
    	for(int i = 0; i < 10 ; i++) {
    		if(imageList.get(i) == null || imageList.get(i).isEmpty() || imageList.get(i).isBlank()) {
    			imagePathList.add("");
    		}else {
    			String result = imageList.get(i).substring(25);
        		imagePathList.add(result);
    		}
    	}
    	
    	model.addAttribute("hashTagList", hashTagList);
    	model.addAttribute("imagePathList", imagePathList);
    	model.addAttribute("loginIdList", loginIdList);
    	model.addAttribute("userName", "しぶたに");
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));

    	return "top";

    }
    
    
    @PostMapping("/postMessage")
    public String post(Model model,PostForm form,@RequestParam String postText, String hashtag, String hashtagSelect, MultipartFile image) {
    	
    	form.setPostText(postText);
    	form.setHashtag(hashtag);
    	form.setHashtagSelect(hashtagSelect);
    	form.setImage(image);
    	
    	PostMessageController pmc = new PostMessageController();
    	
    	List<String> resulList = pmc.postMessage(model, jdbcTemplate, form);
    	List<String> hashTagList = new ArrayList<>();
    	List<String> imagePathList = new ArrayList<>();
    	List<String> loginIdList = new ArrayList<>();
    	for(int i=0; i<10; i++) {
    		hashTagList.add(resulList.get(i));
    	}
    	for(int i=10; i<20; i++) {
    		if(resulList.get(i) == null || resulList.get(i).isEmpty() || resulList.get(i).isBlank()) {
    			imagePathList.add("");
    		}else {
    			String result = resulList.get(i).substring(25);
        		imagePathList.add(result);
    		}
    		    		
    	}
    	for(int i=20; i<30; i++) {
    		loginIdList.add(resulList.get(i));
    	}
    	model.addAttribute("hashTagList", hashTagList);
    	model.addAttribute("imagePathList", imagePathList);
    	model.addAttribute("loginIdList", loginIdList);
    	
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
    	model.addAttribute("userName", "しぶたに");
    	
    	return "top";
      
    }
    
    @RequestMapping(path = "/postDetail")
    public String postDetail(Model model) {
    	
    	return "test";
    }

}
