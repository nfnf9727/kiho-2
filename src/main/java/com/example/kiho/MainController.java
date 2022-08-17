package com.example.kiho;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.kiho.postMessage.PostForm;
import com.example.kiho.postMessage.PostMessageMainLogic;
import com.example.kiho.top.TopMainLogic;
import com.example.kiho.wordSearch.SearchMainLogic;


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
    /**
     * トップ画面出力
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
    	//ログイン時にユーザ情報をsessionに保存するはず
    	//sessionからログイン情報取りに行く　後で追加
    	httpSession.setAttribute("userName", "しぶたに");
    	model.addAttribute("userName", httpSession.getAttribute("userName"));
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));

    	return "top";

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
    	System.out.println(hashtag);
    	System.out.println(hashtagSelect);
    	
    	PostMessageMainLogic pmc = new PostMessageMainLogic();
    	
    	pmc.postMessage(model, jdbcTemplate, form);
    	
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
    	model.addAttribute("userName", "しぶたに");
    	
    	return "top";
      
    }
    
    /**
     * 最新10件より詳細画面へ飛ぶ処理
     * @param model
     * @param no
     * @return
     */
    @PostMapping(path = "/postDetail")
    public String postDetail(Model model,@RequestParam String no) {
    	
    	
    	
    	return "test";
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
    	
    	return "test2";
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
    	
    	return "test2";
    }
    
    /**
     * 「その他の投稿」ボタンから全件出力
     * @param model
     * @return
     */
    @PostMapping(path = "/allPost")
    public String allPost(Model model) {
    	TopMainLogic tml = new TopMainLogic();
    	tml.topHashTag(model, jdbcTemplate);
    	tml.topImagePath(model, jdbcTemplate);
    	tml.topNo(model, jdbcTemplate);
    	tml.topName(model, jdbcTemplate);
    	
    	return "test2";
    }

}
