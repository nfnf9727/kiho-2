package com.example.kiho;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.kiho.mypage.MyPageMainLogic;
import com.example.kiho.postMessage.PostForm;
import com.example.kiho.postMessage.PostMessageMainLogic;
import com.example.kiho.postresult.PostResultMainLogic;
import com.example.kiho.top.TopMainLogic;
import com.example.kiho.wordSearch.SearchMainLogic;


@Controller
public class MainController {
	@Autowired
    private JdbcTemplate jdbcTemplate;

	
	
    /**
     * トップ画面出力(postmsgテーブル作成後、接続してください)
     * PCとモバイルで見え方変更
     * PC：10件テーブルレイアウト　top_pc.html
     * モバイル：スライド式10件　　top_mobile.html
     * 接続URL：http://localhost:8080/top
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(path = "/top")
    public String showIndex(Model model,HttpSession httpSession) {

    	TopMainLogic tc = new TopMainLogic();
    	tc.topHashTag(model, jdbcTemplate);
    	tc.topImagePath(model, jdbcTemplate);
    	tc.topNo(model, jdbcTemplate);
    	tc.topCategory(model, jdbcTemplate);

    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
		
//		//sessionより画面幅取得
//		//画面幅に応じて出力する画面を変える
//		int width = (int) httpSession.getAttribute("width");
//		if(width > 400) {
//			return "top_pc";
//		}else {
//			return "top_mobile";
//		}
		return "top_pc";

    }
    
    /**
     * 投稿
     * @param model
     * @param form
     * @param postText
     * @param hashtag
     * @param hashtagSelect
     * @param image
     * @return
     */
    @PostMapping("/postMessage")
    public String post(Model model,PostForm form,@RequestParam String postText, String hashtag, String hashtagSelect, MultipartFile image) {
    	
    	form.setPostText(postText);
    	form.setHashtag(hashtag);
    	form.setHashtagSelect(hashtagSelect);
    	form.setImage(image);
    	TopMainLogic tc = new TopMainLogic();
    	tc.topCategory(model, jdbcTemplate);
    	
    	PostMessageMainLogic pmc = new PostMessageMainLogic();
    	
    	pmc.postMessage(model, jdbcTemplate, form);
    	
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
    	
//		//sessionより画面幅取得
//		//画面幅に応じて出力する画面を変える
//		int width = (int) httpSession.getAttribute("width");
//		if(width > 400) {
//			return "top_pc";
//		}else {
//			return "top_mobile";
//		}
    	return "top_pc";
      
    }
    
 
    
    /**
     * ワード検索
     * @param model
     * @param wordText　入力内容
     * @return
     */
    @PostMapping(path = "/wordSearch")
    public String wordSearch(Model model,@RequestParam String wordText) {
    	SearchMainLogic sml = new SearchMainLogic();
    	sml.wordSearch(model, jdbcTemplate, wordText);
    	TopMainLogic tc = new TopMainLogic();
    	tc.topCategory(model, jdbcTemplate);
    	
    	return "allposts";
    }
    
    /**
     * カテゴリ検索
     * @param model
     * @param category
     * @return
     */
    @PostMapping(path = "/categorySearch")
    public String categorySearch(Model model,@RequestParam String category) {
    	SearchMainLogic sml = new SearchMainLogic();
    	sml.categorySearch(model, jdbcTemplate, category);
    	TopMainLogic tc = new TopMainLogic();
    	tc.topCategory(model, jdbcTemplate);
    	
    	return "allposts";
    }
    
    /**
     * 「すべての投稿」ボタンから全件出力
     * @param model
     * @return
     */
    @PostMapping(path = "/allPost")
    public String allPost(Model model) {
    	SearchMainLogic sml = new SearchMainLogic();
    	sml.allPost(model, jdbcTemplate);
    	TopMainLogic tc = new TopMainLogic();
    	tc.topCategory(model, jdbcTemplate);
    	
    	return "allposts";
    }

    
    /**
     * マイページ
     * @param model
     * @return
     */
    @RequestMapping(path = "/mypage")
    public String mypage(Model model, HttpSession httpSession) {

    	//sessionからログイン情報取りに行く　後で追加
    	httpSession.setAttribute("loginId", "test1");
    	String loginId = (String) httpSession.getAttribute("loginId");
    	MyPageMainLogic mpml = new MyPageMainLogic();
    	mpml.mypage(model, jdbcTemplate, loginId);
    	TopMainLogic tc = new TopMainLogic();
    	tc.topCategory(model, jdbcTemplate);
    	
    	
    	return "mypage";
    }
    @PostMapping(path = "/postDetail")
    public String postresult(Model model, String no) {
		
	    //sessionから投稿内容取りに行く　後で追加
	    //httpSession.setAttribute("loginId", "test1");
	    //httpSession.setAttribute("createdTime", "2022-08-03 00:00:00");
	    //String loginId = (String) httpSession.getAttribute("loginId");
	    //String createdTime = (String) httpSession.getAttribute("createdTime");
    	
	    PostResultMainLogic prml = new PostResultMainLogic();
	    prml.postresult(model, jdbcTemplate, no);

	return "postresult";
    }
    
    @RequestMapping(path = "/login")
    public String login(Model model) {
	
	return "login";
	
    }
    
    @RequestMapping(path = "/click",method= RequestMethod.POST, params="login")
    public String click(Model model, String uid, String password) {
	
    	if(uid != "197739") {
    		System.out.println("ユーザＩＤがあっていません");
    		model.addAttribute("message","ユーザＩＤがあっていません");
    		return "login";
    	}else{
    	
    	System.out.println(uid);
    	System.out.println(password);

	return "top_pc";
    	}
	
    }
    
    @RequestMapping(path = "/click",method= RequestMethod.POST, params="pwchange")
    public String click2(Model model, String uid, String password) {
	
    	System.out.println(uid);
    	System.out.println(password);

	return "top_pc";
	
    }
    
}
