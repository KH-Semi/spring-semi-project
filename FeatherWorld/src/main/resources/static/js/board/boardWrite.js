// 뒤로 가기 버튼
const backBtn = document.querySelector(".back-button");
// byte 수 표시 div
const byteCounter = document.querySelector(".byte-counter");

function calcBytes(text) {
  const encoder = new TextEncoder();
  const byteArray = encoder.encode(text);
  return byteArray.length;
}

if(backBtn) {

}

if(byteCounter) {

  byteCounter.addEventListener("keydown", () => {

  });
}