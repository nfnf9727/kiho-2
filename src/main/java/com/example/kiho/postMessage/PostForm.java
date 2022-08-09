package com.example.kiho.postMessage;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
	
    @NonNull
    private String postText;

    private String hashtag;
    
    private String hashtagSelect;
    
    private MultipartFile image;

}
