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
			$(".comment-post").append(`<div>◆ ${document.getElementById("loginId").value} ◆</div><br/><div>${data}</div>`);
			$("#comments").val("");  // 入力欄を空にする
		})
		.fail(function() {
			alert("error!");  // 通信に失敗した場合の処理
		})
	});
});