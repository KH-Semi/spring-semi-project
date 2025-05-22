//방명록 목록 조회(ajax)
const selectGuestBookList = () => {
  const cp = 1; // or 현재 페이지 번호
  const ownerNo = document.querySelector("#ownerNo")?.value || 1; // 기본값 1번 주인

  fetch(`/${ownerNo}/guestbook?cp=${cp}`)
    .then((resp) => resp.json())
    .then((guestBookList) => {
      console.log(guestBookList);

      // 화면에 출력하려면 아래처럼 추가
      const container = document.querySelector("#guestbook-list");
      container.innerHTML = "";

      //방명록이 없는 경우
      if (guestBookList.length === 0) {
        container.innerHTML = "<p>등록된 방명록이 없습니다.</p>";
        return;
      }

      //방명록이 있는 경우
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

//방명록 삭제 ( ajax)
const deleteGuestBook = (guestBookNo) => {
  //취소 선택 시
  if (!confirm("삭제 하시겠습니까?")) return;

  fetch(`/${ownerNo}/guestbook`, {
    method: "DELETE",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ guestBookNo: guestBookNo }),
  })
    .then((resp) => resp.text())
    .then((result) => {
      if (result > 0) {
        alert("삭제 되었습니다");
        selectGuestBookList();
      } else {
        alert("삭제 실패");
      }
    });
};

//방명록 수정

// 수정 취소 시 원래 방명록 형태로 돌아가기 위한 백업 변수
let beforeGuestBookRow;

const showUpdateGuestBook = (guestBookNo, btn) => {
  const ownerNo = document.querySelector("#ownerNo")?.value || 1;
  const temp = document.querySelector(".update-textarea");

  if (temp != null) {
    if (
      confirm("수정 중인 방명록이 있습니다. 현재 방명록을 수정 하시겠습니까?")
    ) {
      const guestBookRow = temp.parentElement;
      guestBookRow.after(beforeGuestBookRow);
      guestBookRow.remove();
    } else {
      return;
    }
  }

  const guestBookRow = btn.closest("li");
  beforeGuestBookRow = guestBookRow.cloneNode(true);
  const beforeContent =
    guestBookRow.querySelector(".guestbook-content").innerText;

  guestBookRow.innerHTML = "";

  const textarea = document.createElement("textarea");
  textarea.classList.add("update-textarea");
  textarea.value = beforeContent;
  guestBookRow.append(textarea);

  const btnArea = document.createElement("div");
  btnArea.classList.add("guestbook-btn-area");

  const updateBtn = document.createElement("button");
  updateBtn.innerText = "수정";
  updateBtn.setAttribute("onclick", `updateGuestBook(${guestBookNo}, this)`);

  const cancelBtn = document.createElement("button");
  cancelBtn.innerText = "취소";
  cancelBtn.setAttribute("onclick", "cancelGuestBookUpdate(this)");

  btnArea.append(updateBtn, cancelBtn);
  guestBookRow.append(btnArea);
};

const cancelGuestBookUpdate = (btn) => {
  if (confirm("수정을 취소하시겠습니까?")) {
    const guestBookRow = btn.closest("li");
    guestBookRow.after(beforeGuestBookRow);
    guestBookRow.remove();
  }
};

const updateGuestBook = (guestBookNo, btn) => {
  const textarea = btn.parentElement.previousElementSibling;

  if (textarea.value.trim().length === 0) {
    alert("내용 작성 후 수정 버튼을 클릭해주세요.");
    textarea.focus();
    return;
  }

  const ownerNo = document.querySelector("#ownerNo")?.value || 1;

  const data = {
    guestBookNo: guestBookNo,
    guestBookContent: textarea.value,
  };

  fetch(`/${ownerNo}/guestbook`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((resp) => resp.text())
    .then((result) => {
      if (parseInt(result) > 0) {
        alert("수정되었습니다.");
        selectGuestBookList();
      } else {
        alert("수정 실패");
      }
    })
    .catch((err) => console.error("수정 중 오류:", err));
};
