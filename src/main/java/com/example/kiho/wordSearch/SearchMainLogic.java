package com.example.kiho.wordSearch;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;

public class SearchMainLogic {
	
	/**
	 * ワード検索
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public void wordSearch(Model model, JdbcTemplate jdbcTemplate, String wordText) {

		String noSQL = "SELECT * FROM postmsg WHERE postText LIKE '%" + wordText + "%' OR hashTag LIKE '%" + wordText +"%'";
		List<Map<String,Object>> postList = jdbcTemplate.queryForList(noSQL);
		//沖田追加↓
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

		String noSQL = "SELECT * FROM postmsg WHERE hashTag = '" + category + "'";
		List<Map<String,Object>> postList = jdbcTemplate.queryForList(noSQL);
		//沖田追加↓
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

		String SQL = "SELECT * FROM postmsg";
		List<Map<String,Object>> postList = jdbcTemplate.queryForList(SQL);
		//沖田追加↓
		model.addAttribute("searchmessage","すべての投稿");
		//沖田追加↑
		model.addAttribute("postList", postList);
	}

}
