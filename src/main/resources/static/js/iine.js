$(function() {
	$("#iine_form").on("submit", function(e) {
		e.preventDefault();  // デフォルトのイベント(ページの遷移やデータ送信など)を無効にする
		$.ajax({
			url: $(this).attr("action"),  // リクエストを送信するURLを指定（action属性のurlを抽出）
			type: "POST",  // HTTPメソッドを指定（デフォルトはGET）
			data: {
				iinenum: $("#iinenum").val()  // 送信データ
			}
		})
		.done(function(data) {
			$(".iine-count").remove();  // 元の要素を削除する
			$(".iine-add").append(`<div>${data}</div>`);  // HTMLを追加
			$("#iinebutton").attr('disabled', true);　//いいねボタンを非活性化する
		})
		.fail(function() {
			alert("error!");  // 通信に失敗した場合の処理
		})
	});
});