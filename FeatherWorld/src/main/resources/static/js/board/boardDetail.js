// 1. #boardLike가 클릭 되었을 때
document.querySelector("#boardLike").addEventListener("click", (e) => {
  // 2. 로그인 상태가 아닌 경우 동작 X
  if (loginMemberNo == null) {
    alert("로그인 후 이용해주세요");
    return;
  }

  // 3. 준비된 3개의 변수를 객체로 저장 (JSON 변환 예정)
  const obj = {
    memberNo: loginMemberNo,
    boardNo: boardNo,
    likeCheck: likeCheck,
  };

  // 4. 좋아요 INSERT / DELETE 비동기 요청
  fetch("/board/like", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(obj),
  })
    .then((resp) => resp.text())
    .then((count) => {
      if (count == -1) {
        console.log("좋아요 처리 실패");
        return;
      }

      // 5. likeCheck 값 0 <-> 1 변환
      // -> 클릭 될 때마다 INSERT / DELETE 동작을 번갈아 가면서 하게 끔.
      likeCheck = likeCheck == 0 ? 1 : 0;

      // 6. 하트를 채웠다 / 비웠다 바꾸기
      e.target.classList.toggle("fa-regular"); // 빈 하트
      e.target.classList.toggle("fa-solid"); // 채워진 하트

      // 7. 게시글 좋아요 수 수정
      e.target.nextElementSibling.innerText = count;
    });
});

// 뒤로가기 버튼 처리
document.addEventListener("DOMContentLoaded", () => {
  const boardDetailBackBtn = document.querySelector(".back-button");

  boardDetailBackBtn.addEventListener("click", () => {
    window.history.back();
  });
});

// 수정 버튼 처리
document.addEventListener("DOMContentLoaded", () => {
  const boardDetailEditBtn = document.querySelector("#updateBtn");

  boardDetailEditBtn.addEventListener("click", () => {
    location.href = `/${memberNo}/board/${boardCode}/${boardNo}/update`;
  });
});

// 게시글 작성자의 프로필, 이름 누르면 해당 멤버 홈피 이동
document.addEventListener("DOMContentLoaded", () => {
  // 현재 게시글 작성자 번호 (미리 선언된 전역 변수)
  const writerNo = memberNo; // <script th:inline="javascript">에 선언된 변수 사용

  // DOM에서 프로필 이미지와 작성자 span 태그 가져오기 (img 2개 중 어떤 게 보일지 모르므로 둘 다 처리)
  const memberImg = document.querySelectorAll(".board-writer img");
  const writerName = document.querySelector(".board-writer span");

  // 클릭 이벤트 함수 정의
  const goToMiniHome = () => {
    if (writerNo) {
      location.href = `/${writerNo}/minihome`;
    }
  };

  // 이미지들에 이벤트 바인딩
  memberImg.forEach((img) => {
    img.style.cursor = "pointer";
    img.addEventListener("click", goToMiniHome);
  });

  // 작성자 이름에도 이벤트 바인딩
  if (writerName) {
    writerName.style.cursor = "pointer";
    writerName.addEventListener("click", goToMiniHome);
  }
});
