$(function() {
	$("#comment_form").on("submit", function(e) {
		e.preventDefault();  // デフォルトのイベント(ページの遷移やデータ送信など)を無効にする
		$.ajax({
			url: $(this).attr("action"),  // リクエストを送信するURLを指定（action属性のurlを抽出）
			type: "POST",  // HTTPメソッドを指定（デフォルトはGET）
			data: {
				comments: $("#comments").val(),  // 送信データ
				no: $("#no").val()  // 送信データ
			}
		})
		.done(function(data) {
			$(".comment-post").append(`<form method="post" action="/newCommentdelete"><input type="hidden" name="no" value="${$("#no").val()}"><div>◆ ${document.getElementById("loginName").value} ◆</div><br/><div>${data}</div><br/><input type="hidden" name="comments" value="${data}"><button class="button">削除</button></form>`);
			$("#comments").val("");  // 入力欄を空にする
		})
		.fail(function() {
			alert("error!");  // 通信に失敗した場合の処理
		})
	});
});