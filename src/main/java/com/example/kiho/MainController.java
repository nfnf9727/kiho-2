package com.example.kiho;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class MainController {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    @RequestMapping(path = "/")
    public String showIndex(Model model) {
    	
    	System.out.println("OK");
    	String sql = "SELECT * FROM user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        
        Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
    	model.addAttribute("userName", "しぶたに");
        
        return "top";
    }
    
    
    @PostMapping("/postMessage")
    public String post(Model model,@RequestParam String postText, String hashtag, String hashtagSelect, MultipartFile image) {
    	
    	System.out.println(postText);
    	//セレクトボックスの値：1のとき、テキストボックスの値をハッシュタグに保存
    	//セレクトボックスの値：上記以外、セレクトボックスの値をハッシュタグに保存
    	System.out.println(hashtag);
    	System.out.println(hashtagSelect);
    	System.out.println(image);
    	
    	try {
    	StringBuffer data = new StringBuffer();
        String base64 = new String(Base64.encodeBase64(image.getBytes()),"ASCII");
        data.append("data:image/jpeg;base64,");
        data.append(base64);
        model.addAttribute("base64image",data.toString());
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	Random rnd = new Random();
		model.addAttribute("flg", rnd.nextInt(3));
		
    	return "top";
    }    
    
}
