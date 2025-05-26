// 방명록 목록을 서버에서 조회해서 화면에 렌더링하는 함수
const selectGuestBookList = () => {
  const cp = 1; // 현재 페이지(cp). 현재는 고정값 1. (나중에 페이징 처리용으로 수정 가능)
  const ownerNo = document.querySelector("#ownerNo")?.value || 1;
  // 방명록 주인 번호(ownerNo) 가져옴. 없으면 기본값 1

  // 서버에 방명록 리스트 요청 (비동기 fetch)
  fetch(`/${ownerNo}/guestbook/list?cp=${cp}`) // (05.23 배령 수정)
    .then((resp) => resp.json()) // 응답을 JSON으로 변환
    .then((response) => {
      const guestBookList = response.guestBookList; // 방명록 목록 배열
      const container = document.querySelector("#guestbook-list"); // 방명록이 들어갈 컨테이너 (05.23 배령 수정)
      container.innerHTML = ""; // 기존 목록 초기화 (덮어쓰기)

      // 방명록이 없으면 안내 문구 출력 후 종료
      if (!Array.isArray(guestBookList) || guestBookList.length === 0) {
        container.innerHTML = "<p>등록된 방명록이 없습니다.</p>";
        return;
      }

      // 05.23 배령 수정
      // 방명록이 있을 경우, 각각의 방명록 데이터를 순회하며 DOM 요소 생성
      guestBookList.forEach((item) => {

        // 🔒 비밀글인데, 홈피 주인도 아니고 작성자도 아니면 랜더링 x
        if (
          item.secret == 1 &&
          loginMemberNo != item.visitor?.memberNo &&
          loginMemberNo != ownerNo
        ) {
          return;
        }

        const itemDiv = document.createElement("div");
        itemDiv.className = "guestbook-item"; // 전체 방명록 한 개의 최상위 div

        const wrapDiv = document.createElement("div");
        wrapDiv.className = "guestbook-wrap"; // 내부 컨텐츠를 감싸는 wrapper

        const mainDiv = document.createElement("div");
        mainDiv.className = "guestbook-main"; // 실제 내용 부분을 감싸는 div

        const contentDiv = document.createElement("div");
        contentDiv.className = "guestbook-content"; // 방명록 내용 표시 영역
        contentDiv.textContent = item.guestBookContent; // 내용 삽입

        mainDiv.appendChild(contentDiv); // main 안에 내용 삽입
        wrapDiv.appendChild(mainDiv); // wrap 안에 main 삽입
        itemDiv.appendChild(wrapDiv); // item 안에 wrap 삽입

        const infoDiv = document.createElement("div");
        infoDiv.className = "guestbook-info"; // 작성자 및 날짜 정보 영역

        const writerSpan = document.createElement("span");
        writerSpan.textContent = item.visitor?.memberName || "익명"; // 작성자 이름 (없으면 '익명')

        const dateDiv = document.createElement("div");
        dateDiv.className = "guestbook-date"; // 작성일 표시 영역
        dateDiv.textContent = item.guestBookWriteDate; // 작성일 삽입

        // 작성자, 작성일을 info 영역에 추가
        infoDiv.appendChild(writerSpan);
        infoDiv.appendChild(dateDiv);

        // 전체 item div에 info 추가
        itemDiv.appendChild(infoDiv);

        // 최종적으로 guestbook-list 영역에 추가
        container.appendChild(itemDiv);

        // 로그인한 사용자 정보 가져오기
        const loginMemberNo = document.querySelector("#loginMemberNo")?.value;

        // 작성자 번호와 로그인한 사용자가 같을 경우에만 버튼 표시
        // 이거 근데 기존 edit, delete 버튼이랑 다르게 나오네;;;
        if (loginMemberNo && parseInt(loginMemberNo) === item.visitorNo) {
          const actionDiv = document.createElement("div");
          actionDiv.className = "guestbook-actions";

          const editBtn = document.createElement("button");
          editBtn.textContent = "Edit";
          editBtn.addEventListener("click", () =>
            showUpdateGuestBook(item.guestBookNo, editBtn)
          );

          const deleteBtn = document.createElement("button");
          deleteBtn.textContent = "Delete";
          deleteBtn.addEventListener("click", () =>
            deleteGuestBook(item.guestBookNo)
          );

          actionDiv.appendChild(editBtn);
          actionDiv.appendChild(deleteBtn);
          infoDiv.appendChild(actionDiv);
        }

        itemDiv.appendChild(infoDiv);
        container.appendChild(itemDiv);
      });
    })
    .catch((err) => {
      console.error("방명록 목록 조회 실패:", err); // 요청 실패 시 콘솔 출력
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
      secret: document.querySelector("#secretCheck").checked ? 1 : 0,
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
  const ownerNo = document.querySelector("#ownerNo")?.value || 1;

  fetch(`/${ownerNo}/guestbook`, {
    method: "DELETE",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ guestBookNo: guestBookNo }),
  })
    .then((resp) => resp.json()) // json()으로 받아 result.success를 명확히 확인해야 함
    .then((result) => {
      console.log("삭제 응답:", result); // 응답 형식 확인
      // 05.23 수정 배령
      if (result.success === true || result.success === "true") {
        alert("삭제 되었습니다");
        selectGuestBookList();
      } else {
        alert("삭제 실패");
      }
    })
    .catch((err) => {
      console.error("삭제 오류: ", err);
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

// 페이지 네이션
/** 페이징 목록/글쓰기 버튼 포함 div 생성 메서드
 * @author Jiho
 * @param pagination Pagination 객체
 * @return 페이징 목록/글쓰기 버튼 포함 div
 */
const createBoardFooter = (pagination) => {
  /** 각각의 페이징 목록을 생성하고, 페이지 변경 click 이벤트 부여
   * @author Jiho
   * @param {number} page 페이지 번호(cp)
   * @param {String} text innerText 내용
   * @param {string} className 클래스명
   * @returns {HTMLSpanElement} span 태그
   */
  const createPageSpan = (page, text, className = "") => {
    const span = document.createElement("span");
    span.innerText = text;
    span.dataset.page = String(page);
    if (className) span.classList.add(className);

    // 페이지 클릭 이벤트 추가
    span.addEventListener("click", async () => {
      // 변경된 cp값 적용/history 저장
      recodeCp(page);
      // 해당 페이지에 맞게 게시글/페이징 목록 갱신
      renderBoardList(boardCode, page).catch(console.error);
    });

    return span;
  };

  const containerDiv = document.createElement("div");

  if (pagination) {
    // 임시 페이징 목록 div
    const updatedPagination = document.createElement("div");
    updatedPagination.classList.add("pagination");

    // << 첫 페이지
    updatedPagination.append(createPageSpan(1, "<<"));

    // < 이전 페이지
    updatedPagination.append(
      createPageSpan(pagination.prevPage, "<", "page-nav")
    );

    // 페이지 번호 목록
    for (let i = pagination.startPage; i <= pagination.endPage; i++) {
      const span = createPageSpan(i, i);
      if (i === pagination.currentPage) {
        span.classList.add("current");
      }
      updatedPagination.append(span);
    }

    // > 다음 페이지
    updatedPagination.append(
      createPageSpan(pagination.nextPage, ">", "page-nav")
    );

    // >> 마지막 페이지
    updatedPagination.append(createPageSpan(pagination.maxPage, ">>"));

    containerDiv.append(updatedPagination);
  }
};

document.addEventListener("DOMContentLoaded", () => {
  const lockIcon = document.querySelector("#lockIcon");
  const toggleBtn = document.querySelector("#toggleSecret");
  const secretCheck = document.querySelector("#secretCheck");

  toggleBtn.addEventListener("click", () => {
    secretCheck.checked = !secretCheck.checked;

    // 🔒 좌물쇠 아이콘 전환
    lockIcon.classList.remove("fa-lock", "fa-lock-open");
    lockIcon.classList.add(secretCheck.checked ? "fa-lock" : "fa-lock-open");

    // 🔄 토글 아이콘 방향 전환 (ON = 오른쪽 = 비밀글 O)
    toggleBtn.classList.remove("fa-toggle-on", "fa-toggle-off");
    toggleBtn.classList.add(
      secretCheck.checked ? "fa-toggle-on" : "fa-toggle-off"
    );
  });
});
