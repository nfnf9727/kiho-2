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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.kiho.login.LoginMainLogic;
import com.example.kiho.mypage.MyPageMainLogic;
import com.example.kiho.postMessage.PostForm;
import com.example.kiho.postMessage.PostMessageMainLogic;
import com.example.kiho.postresult.CommentAddMainLogic;
import com.example.kiho.postresult.IineAddMainLogic;
import com.example.kiho.postresult.PostDeleteMainLogic;
import com.example.kiho.postresult.PostResultMainLogic;
import com.example.kiho.pwchange.PwchangeMainLogic;
import com.example.kiho.top.TopMainLogic;
import com.example.kiho.wordSearch.SearchMainLogic;

@Controller
public class MainController {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * トップ画面出力(postmsgテーブル作成後、接続してください) PCとモバイルで見え方変更 PC：10件テーブルレイアウト top_pc.html
	 * モバイル：スライド式10件 top_mobile.html 接続URL：http://localhost:8080/top
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(path = "/top")
	public String showIndex(Model model, HttpSession httpSession) {

		TopMainLogic tc = new TopMainLogic();
		String flg = tc.sessionCheck(httpSession);
		if ("1".equals(flg)) {
			return "login";
		} else {
			tc.topHashTag(model, jdbcTemplate);
			tc.topImagePath(model, jdbcTemplate);
			tc.topNo(model, jdbcTemplate);
			tc.topCategory(model, jdbcTemplate);

			Random rnd = new Random();
			model.addAttribute("flg", rnd.nextInt(3));

			// sessionより画面幅取得
			// 画面幅に応じて出力する画面を変える
			int width = (int) httpSession.getAttribute("width");
			if (width > 400) {
				return "top_pc";
			} else {
				return "top_mobile";
			}

		}

	}

	/**
	 * 投稿
	 * 
	 * @param model
	 * @param form
	 * @param postText
	 * @param hashtag
	 * @param hashtagSelect
	 * @param image
	 * @return
	 */
	@PostMapping("/postMessage")
	public String post(Model model, PostForm form, @RequestParam String postText, String hashtag, String hashtagSelect,
			MultipartFile image, HttpSession httpSession) {

		TopMainLogic tc = new TopMainLogic();
		String flg = tc.sessionCheck(httpSession);
		if ("1".equals(flg)) {
			return "login";
		} else {
			form.setPostText(postText);
			form.setHashtag(hashtag);
			form.setHashtagSelect(hashtagSelect);
			form.setImage(image);
			tc.topCategory(model, jdbcTemplate);
			PostMessageMainLogic pmc = new PostMessageMainLogic();
			String loginId = (String) httpSession.getAttribute("loginId");
			pmc.postMessage(model, jdbcTemplate, form, loginId);
			Random rnd = new Random();
			model.addAttribute("flg", rnd.nextInt(3));

			// sessionより画面幅取得
			// 画面幅に応じて出力する画面を変える
			int width = (int) httpSession.getAttribute("width");
			if (width > 400) {
				return "top_pc";
			} else {
				return "top_mobile";
			}

		}

	}

	/**
	 * ワード検索
	 * 
	 * @param model
	 * @param wordText 入力内容
	 * @return
	 */
	@PostMapping(path = "/wordSearch")
	public String wordSearch(Model model, @RequestParam String wordText) {
		SearchMainLogic sml = new SearchMainLogic();
		sml.wordSearch(model, jdbcTemplate, wordText);
		TopMainLogic tc = new TopMainLogic();
		tc.topCategory(model, jdbcTemplate);

		return "allposts";
	}

	/**
	 * カテゴリ検索
	 * 
	 * @param model
	 * @param category
	 * @return
	 */
	@PostMapping(path = "/categorySearch")
	public String categorySearch(Model model, @RequestParam String category) {
		SearchMainLogic sml = new SearchMainLogic();
		sml.categorySearch(model, jdbcTemplate, category);
		TopMainLogic tc = new TopMainLogic();
		tc.topCategory(model, jdbcTemplate);

		return "allposts";
	}

	/**
	 * 「すべての投稿」ボタンから全件出力
	 * 
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
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/mypage")
	public String mypage(Model model, HttpSession httpSession) {

		TopMainLogic tc = new TopMainLogic();
		String flg = tc.sessionCheck(httpSession);
		if ("1".equals(flg)) {
			return "login";
		} else {
			// sessionからログイン情報取りに行く 後で追加
			String loginId = (String) httpSession.getAttribute("loginId");
			MyPageMainLogic mpml = new MyPageMainLogic();
			mpml.mypage(model, jdbcTemplate, loginId);
			tc.topCategory(model, jdbcTemplate);

			return "mypage";
		}
	}
	
	/**
	 * マイページからニックネーム変更
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(path = "/nickname")
	public String nickname(Model model, HttpSession httpSession) {

		TopMainLogic tc = new TopMainLogic();
		String flg = tc.sessionCheck(httpSession);
		if ("1".equals(flg)) {
			return "login";
		} else {
			// sessionからログイン情報取りに行く 後で追加
			String loginId = (String) httpSession.getAttribute("loginId");
			MyPageMainLogic mpml = new MyPageMainLogic();
			mpml.mypage(model, jdbcTemplate, loginId);
			tc.topCategory(model, jdbcTemplate);

			return "mypage";
		}
	}
	
	/**
	 * マイページからパス変更
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(path = "/changePass")
	public String click(Model model, HttpSession httpSession) {

		String loginId = (String) httpSession.getAttribute("loginId");
		model.addAttribute("loginId", loginId);
		System.out.println(loginId);
		
		return "password";

	}

	@PostMapping(path = "/postDetail")
	public String postresult(Model model, HttpSession httpSession, String no) {

		TopMainLogic tc = new TopMainLogic();
		tc.topCategory(model, jdbcTemplate);
		String sessionloginId = (String) httpSession.getAttribute("loginId");
		PostResultMainLogic prml = new PostResultMainLogic();
		prml.postresult(model, jdbcTemplate, sessionloginId, no);

		return "postresult";
	}

	@RequestMapping(path = "/logout")
	public String logout(HttpSession httpSession) {

		httpSession.invalidate();
		return "login";
	}

	// ログイン画面呼び出し
	@RequestMapping(path = "/login")
	public String login(Model model) {
		return "login";
	}

	// ログイン画面
	// ログインボタン入力時の動作

	@RequestMapping(path = "/click", method = RequestMethod.POST, params = "login")
	public String click1(Model model, String uid, String password, int widthScreen, HttpSession httpSession) {

		System.out.println(widthScreen);
		httpSession.setAttribute("width", widthScreen);
		// インプットチェック

		LoginMainLogic lml = new LoginMainLogic();
		int errsw1 = lml.uidCheck(model, jdbcTemplate, uid, password);

		if (errsw1 == 1) {
			System.out.println(uid);
			System.out.println(password);
			return "login";
		}

		// 初期パスワード強制変更

		LoginMainLogic lml2 = new LoginMainLogic();
		int errsw2 = lml2.prePass(model, jdbcTemplate, uid, password);

		if (errsw2 == 1) {
			System.out.println(uid);
			System.out.println(password);
			return "password";
		}

		LoginMainLogic lml3 = new LoginMainLogic();
		lml3.LastLogin(model, jdbcTemplate, uid);
		System.out.println(uid);
		System.out.println(password);

		httpSession.setAttribute("loginId", uid);
		
		TopMainLogic tc = new TopMainLogic();
		tc.topHashTag(model, jdbcTemplate);
		tc.topImagePath(model, jdbcTemplate);
		tc.topNo(model, jdbcTemplate);
		tc.topCategory(model, jdbcTemplate);

		Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));

		// sessionより画面幅取得
		// 画面幅に応じて出力する画面を変える
		int width = (int) httpSession.getAttribute("width");
		if (width > 400) {
			return "top_pc";
		} else {
			return "top_mobile";
		}

	}

	// ログイン画面
	// パスワード変更ボタン入力時の動作

	@RequestMapping(path = "/click", method = RequestMethod.POST, params = "pwchange")
	public String click(Model model, String uid, String password, int widthScreen, HttpSession httpSession) {

		// インプットチェック

		LoginMainLogic lml = new LoginMainLogic();
		int errsw1 = lml.uidCheck(model, jdbcTemplate, uid, password);

		if (errsw1 == 1) {
			System.out.println(uid);
			System.out.println(password);
			return "login";
		}

		model.addAttribute("loginId", uid);
		System.out.println(uid);
		System.out.println(password);
		httpSession.setAttribute("width", widthScreen);
		return "password";

	}

	// ログイン画面
	// パスワード再登録確認リンク入力時の動作

	@RequestMapping("/click")
	public String kakunin(Model model, @RequestParam String uid, int widthScreen, HttpSession httpSession) {

		// インプットチェック

		LoginMainLogic lml = new LoginMainLogic();
		int errsw3 = lml.uidCheck2(model, jdbcTemplate, uid);

		if (errsw3 == 1) {
			System.out.println(uid);
			return "login";
		}

		model.addAttribute("loginId", uid);
		System.out.println(uid);
		httpSession.setAttribute("width", widthScreen);

		return "kakunin";

	}

	// パスワード変更画面呼び出し
	@RequestMapping(path = "/password")
	public String password(HttpSession httpSession) {

		return "password";
	}

	@RequestMapping(path = "/password", method = RequestMethod.POST, params = "change")
	public String password(Model model, String loginId, String newpass, String newpass2, HttpSession httpSession) {

		// インプットチェック

		PwchangeMainLogic lml = new PwchangeMainLogic();
		int errsw1 = lml.passCheck(model, jdbcTemplate, loginId, newpass, newpass2);

		if (errsw1 == 1) {
			System.out.println(newpass);
			System.out.println(newpass2);
			return "password";
		}

		System.out.println(newpass);
		System.out.println(newpass2);
		
		httpSession.setAttribute("loginId", loginId);
		TopMainLogic tc = new TopMainLogic();
		tc.topHashTag(model, jdbcTemplate);
		tc.topImagePath(model, jdbcTemplate);
		tc.topNo(model, jdbcTemplate);
		tc.topCategory(model, jdbcTemplate);

		Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));

		// sessionより画面幅取得
		// 画面幅に応じて出力する画面を変える
		int width = (int) httpSession.getAttribute("width");
		if (width > 400) {
			return "top_pc";
		} else {
			return "top_mobile";
		}

	}

	// パスワード再登録確認画面呼び出し
	@RequestMapping(path = "/kakunin")
	public String kakunin(Model model) {

		return "kakunin";
	}

	// パスワード再登録確認画面 ⇒ パスワード変更画面呼び出し
	@RequestMapping(path = "/pwclear", method = RequestMethod.POST, params = "pwclear")
	public String pwclear(Model model, String loginId) {

		model.addAttribute("loginId", loginId);
		return "password";
	}

	/**
	 * 投稿内容削除 --2022.9.9 hatano Add--
	 * 
	 * @parm model
	 * @parm no
	 * @return
	 */
	@PostMapping(path = "/postdelete")
	public String postDelete(Model model, @RequestParam int no, HttpSession httpSession) {
		System.out.println("postDeleteメソッド開始");
//		投稿内容削除の実行
		PostDeleteMainLogic pdml = new PostDeleteMainLogic();
		pdml.postDeleteMainLogic(model, jdbcTemplate, no);
//		トップ画面へ遷移
		TopMainLogic tc = new TopMainLogic();
		tc.topHashTag(model, jdbcTemplate);
		tc.topImagePath(model, jdbcTemplate);
		tc.topNo(model, jdbcTemplate);
		tc.topCategory(model, jdbcTemplate);

		Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));

		// sessionより画面幅取得
		// 画面幅に応じて出力する画面を変える
		int width = (int) httpSession.getAttribute("width");
		if (width > 400) {
			return "top_pc";
		} else {
			return "top_mobile";
		}
	}

	/**
	 * コメント投稿 --2022.9.15 hatano Add
	 * 
	 * @parm comments
	 * @return
	 */
	@PostMapping("/comment-post")
	@ResponseBody
	public String commentPost(@RequestParam String comments, int no, HttpSession httpSession) {
		System.out.println(no);
        //commentテーブルの更新が必要
		CommentAddMainLogic caml = new CommentAddMainLogic();
		String loginId = (String) httpSession.getAttribute("loginId");
		caml.commentAddMainLogic(jdbcTemplate, no, loginId, comments);

		return comments;
	}

	/**
	 * いいね！更新 --2022.9.15 hatano Add
	 * 
	 * @parm iine
	 * @return
	 */
	@PostMapping("/iineadd")
	@ResponseBody
	public int iineAdd(@RequestParam int iinenum, int no) {
		System.out.println("iineAddメソッド開始");
		System.out.println(iinenum);
		System.out.println(no);
//		postmsgテーブルのいいね数の更新が必要
		IineAddMainLogic iaml = new IineAddMainLogic();
		iaml.iineAddMainLogic(jdbcTemplate, no);
		return iinenum += 1;
	}

}