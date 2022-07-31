package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
	
    @RequestMapping(path = "/")
    public String showIndex(Model model) {
    	System.out.println("OK");
    	model.addAttribute("userName", "しぶたに");
        return "index";
    }
    
    
    @PostMapping("/postMessage")
    public String post(@RequestParam String postText, String hashtag, String hashtagSelect) {
    	
    	System.out.println(postText);
    	//セレクトボックスの値：1のとき、テキストボックスの値をハッシュタグに保存
    	//セレクトボックスの値：上記以外、セレクトボックスの値をハッシュタグに保存
    	System.out.println(hashtag);
    	System.out.println(hashtagSelect);
    	
    	return "index";
    }

}
