package com.example.kiho.wordSearch;

import java.util.ArrayList;
import java.util.List;

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

		String noSQL = "SELECT no FROM postmsg WHERE postText LIKE '%" + wordText + "%'";
		List<String> noList = jdbcTemplate.queryForList(noSQL, String.class);
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE postText LIKE '%" + wordText + "%'";
		List<String> loginIdList = jdbcTemplate.queryForList(loginIdSQL, String.class);
		List<String> hashTagList = new ArrayList<>();
		List<String> imagePathList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		for (int i = 0; i < noList.size(); i++) {
			String hashTagSQL = "SELECT hashTag FROM postmsg WHERE no = '" + noList.get(i) + "'";
			String hashTagStr = jdbcTemplate.queryForObject(hashTagSQL, String.class);
			hashTagList.add(hashTagStr);

			String imageSQL = "SELECT image FROM postmsg WHERE no = '" + noList.get(i) + "'";
			String imageStr = jdbcTemplate.queryForObject(imageSQL, String.class);
			imagePathList.add(imageStr);

			String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginIdList.get(i) + "'";
			String nameStr = jdbcTemplate.queryForObject(nameSQL, String.class);
			nameList.add(nameStr);
		}
		model.addAttribute("noList", noList);
		model.addAttribute("hashTagList", hashTagList);
		model.addAttribute("imagePathList", imagePathList);
		model.addAttribute("nameList", nameList);
	}
	
	/**
	 * カテゴリ検索
	 * @param model
	 * @param jdbcTemplate
	 * @param wordText
	 */
	public void categorySearch(Model model, JdbcTemplate jdbcTemplate, String category) {

		String noSQL = "SELECT no FROM postmsg WHERE hashTag = '" + category + "'";
		List<String> noList = jdbcTemplate.queryForList(noSQL, String.class);
		String loginIdSQL = "SELECT loginId FROM postmsg WHERE hashTag = '" + category + "'";
		List<String> loginIdList = jdbcTemplate.queryForList(loginIdSQL, String.class);
		List<String> hashTagList = new ArrayList<>();
		List<String> imagePathList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		for (int i = 0; i < noList.size(); i++) {
			String hashTagSQL = "SELECT hashTag FROM postmsg WHERE no = '" + noList.get(i) + "'";
			String hashTagStr = jdbcTemplate.queryForObject(hashTagSQL, String.class);
			hashTagList.add(hashTagStr);

			String imageSQL = "SELECT image FROM postmsg WHERE no = '" + noList.get(i) + "'";
			String imageStr = jdbcTemplate.queryForObject(imageSQL, String.class);
			imagePathList.add(imageStr);

			String nameSQL = "SELECT name FROM user WHERE loginId = '" + loginIdList.get(i) + "'";
			String nameStr = jdbcTemplate.queryForObject(nameSQL, String.class);
			nameList.add(nameStr);
		}
		model.addAttribute("noList", noList);
		model.addAttribute("hashTagList", hashTagList);
		model.addAttribute("imagePathList", imagePathList);
		model.addAttribute("nameList", nameList);
	}

}
