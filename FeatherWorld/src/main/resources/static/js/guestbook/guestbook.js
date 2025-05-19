//방명록 목록 조회(ajax)
const selectGuestBookList = () => {
  const cp = 1; // or 현재 페이지 번호
  const ownerNo = document.querySelector("#ownerNo")?.value || 1; // 기본값 1번 주인

  fetch(`/guestbook?cp=${cp}&ownerNo=${ownerNo}`)
    .then(resp => resp.json())
    .then(guestBookList => {
      console.log(guestBookList);

      // 화면에 출력하려면 아래처럼 추가
      const container = document.querySelector("#guestbook-list");
      container.innerHTML = "";

      guestBookList.forEach(item => {
        const div = document.createElement("div");
        div.textContent = item.guestBookContent;
        container.appendChild(div);
      });
    })
    .catch(err => {
      console.error("방명록 조회 실패:", err);
    });
};







//방명록 등록 (ajax)





//방명록 삭제(ajax)




//방명록 수정 (ajax)