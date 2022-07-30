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
 
 
var select = document.getElementById("hashtagSelect");
select.onchange = function(){
  if (this.value == 1) {
    document.getElementById("hashtag").disabled = false;
    document.getElementById("hashtag").value = "";
  }else {
    document.getElementById("hashtag").disabled = true;
 
    if(this.value == 2){
      document.getElementById("hashtag").value = "#趣味";
    }else if (this.value == 3){
      document.getElementById("hashtag").value = "#休日";
    }else{
      document.getElementById("hashtag").value = "#ランチ";
    }
 
  }
}