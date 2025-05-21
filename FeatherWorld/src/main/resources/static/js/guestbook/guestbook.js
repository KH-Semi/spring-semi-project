//방명록 목록 조회(ajax)
const selectGuestBookList = () => {
  const cp = 1; // or 현재 페이지 번호
  const ownerNo = document.querySelector("#ownerNo")?.value || 1; // 기본값 1번 주인

  fetch(`/${ownerNo}/guestbook`)
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
// 방명록 등록 (ajax)
document.addEventListener("DOMContentLoaded", () => {
  const guestBookContent = document.querySelector("#guestBookContent");
  const addGuestBook = document.querySelector("#addGuestBook");
  const loginMemberNo = document.querySelector("#loginMemberNo")?.value || null;

  addGuestBook.addEventListener("click", () => {
    // 방명록 등록 버튼 클릭 시
    if (loginMemberNo === null) {
      alert("로그인 후 작성 가능합니다.");
      return;
    }
    // 댓글 내용이 작성되지 않은 경우(textarea 비우고 눌렀을 때)
    if (guestBookContent.value.trim().length === 0) {
      alert("내용 작성 후 등록 버튼 클릭해주세요");
      guestBookContent.focus();
      return;
    }

    const ownerNo = document.querySelector("#ownerNo")?.value || 1; // gpt 추천 문구

    //ajax를 이용해 방명록 등록 요청
    const data = {
      guestBookContent: guestBookContent.value,
      visitorNo: loginMemberNo,
    };

    fetch(`/${ownerNo}/guestbook`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    })
      .then((resp) => resp.json())
      .then((result) => {
        console.log(result);

        if (result < 0) {
          alert("방명록 등록 실패");
        } else {
          alert("방명록이 등록되었습니다.");
          selectGuestBookList(); // 방명록 목록을 다시 조회해서 화면에 출력
          guestBookContent.value = ""; // textarea에 작성한 방명록 내용 지우기
        }
      })
      .catch((err) => console.log("에러 발생:", err));
  });
});
