//방명록 목록 조회(ajax)
const selectGuestBookList = () => {
  const cp = 1; // or 현재 페이지 번호
  const ownerNo = document.querySelector("#ownerNo")?.value || 1; // 기본값 1번 주인

  fetch(`/guestbook?cp=${cp}&ownerNo=${ownerNo}`)
    .then((resp) => resp.json())
    .then((guestBookList) => {
      console.log(guestBookList);

      // 화면에 출력하려면 아래처럼 추가
      const container = document.querySelector("#guestbook-list");
      container.innerHTML = "";

      guestBookList.forEach((item) => {
        const div = document.createElement("div");
        div.textContent = item.guestBookContent;
        container.appendChild(div);
      });
    })
    .catch((err) => {
      console.error("방명록 조회 실패:", err);
    });
};

//방명록 작성
document.addEventListener('DOMContentLoaded', function () {
  const writeBtn = document.querySelector('#addGuestBookBtn');
  const textArea = document.querySelector('.guestBook-textbox');
  const ownerNo = /*[[${ownerNo}]]*/ 0; // 타임리프로 주입

  writeBtn.addEventListener('click', function () {
    const content = textArea.value.trim();
    if (content === '') {
      alert('내용을 입력해주세요.');
      return;
    }

    fetch(`/${ownerNo}/guestbook`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ guestBookContent: content })
    })
      .then(response => response.text())
      .then(result => {
        if (result == 1) {
          alert('방명록이 등록되었습니다.');
          location.reload();
        } else {
          alert('등록 실패');
        }
      })
      .catch(error => {
        console.error('오류 발생:', error);
      });
  });
});
