const buttonOpen = document.getElementById('modalOpen');
const modal = document.getElementById('easyModal');
const buttonClose = document.getElementsByClassName('modalClose')[0];

// ボタンがクリックされた時
buttonOpen.addEventListener('click', modalOpen);
function modalOpen() {
	modal.style.display = 'block';
}

// バツ印がクリックされた時
buttonClose.addEventListener('click', modalClose);
function modalClose() {
	modal.style.display = 'none';
}

// モーダルコンテンツ以外がクリックされた時
addEventListener('click', outsideClose);
function outsideClose(e) {
	if (e.target == modal) {
		modal.style.display = 'none';
	}
}


document.querySelector('#input').addEventListener('change', (event) => {
	const file = event.target.files[0]

	// fileがundefinedの時にreader.readAsDataURL(file)がエラーになるため、
	// !fileがfalseの場合にreturnする。
	if (!file) return

	const reader = new FileReader()

	reader.onload = (event) => {
		document.querySelector('#img').src = event.target.result
	}

	reader.readAsDataURL(file)
})

var select = document.getElementById("hashtagSelect");
select.onchange = function() {
	if (this.value == 0) {
		document.getElementById("hashtag").disabled = false;
		document.getElementById("hashtag").value = "";
	} else {
		document.getElementById("hashtag").disabled = true;
		document.getElementById("hashtag").value = this.value;
	}
}

// 入力チェック
function inputCheck() {
	var str1 = document.postMsg.postText.value;
	if (document.postMsg.postText.value == "") {
		alert('投稿内容を入力してください');
		return false;
	} else if (document.postMsg.hashtagSelect.value == "0") {
		if (document.postMsg.hashtag.value == "") {
			alert('ハッシュタグを入力、もしくはセレクトボックスで選択ください');
			return false;
		} else if (document.postMsg.hashtag.value != "") {
			var str = document.postMsg.hashtag.value;
			var result = str.substr(0, 1);
			if (result != '#') {
				alert('ハッシュタグは、「#」から入力してください');
				return false;
			} else if (str.length > 30) {
				alert('ハッシュタグは30文字以内で入力してください');
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	} else if (str1.length > 120) {
		alert('投稿内容は120文字以内で入力してください');
		return false;
	}
}




$('.slider').slick({
	autoplay: true,//自動的に動き出すか。初期値はfalse。
	infinite: true,//スライドをループさせるかどうか。初期値はtrue。
	speed: 500,//スライドのスピード。初期値は300。
	slidesToShow: 3,//スライドを画面に3枚見せる
	slidesToScroll: 1,//1回のスクロールで1枚の写真を移動して見せる
	prevArrow: '<div class="slick-prev"></div>',//矢印部分PreviewのHTMLを変更
	nextArrow: '<div class="slick-next"></div>',//矢印部分NextのHTMLを変更
	centerMode: true,//要素を中央ぞろえにする
	variableWidth: true,//幅の違う画像の高さを揃えて表示
	dots: true,//下部ドットナビゲーションの表示
});



$('.slider').slick({
	autoplay: true,
	infinite: true,
	speed: 500,
	slidesToShow: 3,
	slidesToScroll: 1,
	prevArrow: '<div class="slick-prev"></div>',
	nextArrow: '<div class="slick-next"></div>',
	centerMode: true,
	variableWidth: true,
	dots: true,
});