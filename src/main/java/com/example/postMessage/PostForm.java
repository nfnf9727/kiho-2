package com.example.postMessage;

import org.springframework.web.multipart.MultipartFile;


public class PostForm {
	
    private String postText;

    private String hashtag;
    
    private String hashtagSelect;
    
    private MultipartFile image;

	public String getPostText() {
		return postText;
	}

	public String getHashtag() {
		return hashtag;
	}

	public String getHashtagSelect() {
		return hashtagSelect;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public void setHashtagSelect(String hashtagSelect) {
		this.hashtagSelect = hashtagSelect;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
