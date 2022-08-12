package com.example.kiho.postMessage;



public class PostCheck {

	public String postCheck(PostForm form) {
		
		String str = null;
		String errorMsg = "";
		if(str == form.getPostText() || form.getPostText().isBlank()) {
			errorMsg = "※投稿内容は必須入力です。正しく入力してください。";
		}
		
		if(form.getHashtagSelect().equals("1")) {
			if(str == form.getHashtag()) {
				errorMsg = "※ハッシュタグを入力、もしくはセレクトボックスで選択してください。";
			}else if(form.getHashtag().isBlank()) {
				errorMsg = "※ハッシュタグはスペースもしくは空欄はNGです";
			}else if(form.getHashtag().length() > 30) {
				errorMsg = "※ハッシュタグは30文字以内で入力してください";
			}else {
				String hashTag = form.getHashtag();
				String result = hashTag.substring(0,1);
				if(!result.equals("#")) {
					errorMsg = "※「#」は半角で入力してください。";
				}
			}
		}

	if(form.getPostText().length()>120)

	{
		errorMsg = "※投稿内容は120文字以内で入力してください。";
	}

	return errorMsg;
}

}
