package com.example.kiho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
