document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.querySelector(".member-search");
  const searchResults = document.getElementById("searchResults");
  const closeBtn = document.getElementById("closeBtn");

  // 검색 입력에 이벤트 리스너 추가 - 즉시 검색 실행
  searchInput.addEventListener("input", function () {
    const searchTerm = this.value.trim();

    // 입력 값이 없으면 결과와 닫기 버튼 숨김
    if (searchTerm === "") {
      searchResults.style.display = "none";
      closeBtn.style.display = "none";
      return;
    }

    // 검색어가 있으면 닫기 버튼 표시
    closeBtn.style.display = "inline";

    // 즉시 검색 실행
    fetch(`/search?memberName=${encodeURIComponent(searchTerm)}`)
      //encodeURIComponent()는 URL에 포함될 때 문제가 될 수 있는
      //특수문자(공백, ?, &, =, %, 한글 등)를 안전한 형식으로 변환합니다.

      .then((response) => response.json())
      .then((data) => {
        // 검색 결과 표시
        displayResults(data);
      })
      .catch((error) => {
        console.error("검색 중 오류 발생:", error);
      });
  });

  // 검색 결과 표시 함수
  function displayResults(members) {
    searchResults.innerHTML = ""; // 이전 결과 비우기

    if (members.length === 0) {
      const li = document.createElement("li");
      li.textContent = "검색 결과가 없습니다.";
      searchResults.appendChild(li);
    } else {
      members.forEach((member) => {
        const li = document.createElement("li");
        li.className = "member-result-item";

        // 결과 항목을 구성하는 HTML 생성
        li.innerHTML = `
        <div class="member-result-wrapper">
          <div class="member-result-avatar">
            <img src="${member.memberImg || "/images/user.png"}" alt="${
          member.memberName
        }">
          </div>
          <div class="member-result-info">
            <div class="member-result-name">${member.memberName}</div>
            <div class="member-result-email">${member.memberEmail}</div>
          </div>
        </div>
      `;

        // 회원 클릭 시 해당 미니홈으로 이동
        li.addEventListener("click", () => {
          window.location.href = `/${member.memberNo}/minihome`;
        });

        searchResults.appendChild(li);
      });
    }

    searchResults.style.display = "block";
  }
  // 닫기 버튼 클릭 이벤트
  closeBtn.addEventListener("click", function () {
    searchInput.value = ""; // 입력 내용 지우기
    searchResults.style.display = "none"; // 결과 숨기기
    closeBtn.style.display = "none"; // 닫기 버튼 숨기기
  });

  // 검색창 외부 클릭 시 결과 숨기기
  document.addEventListener("click", function (event) {
    if (
      !searchInput.contains(event.target) &&
      !searchResults.contains(event.target) &&
      event.target !== closeBtn
    ) {
      searchResults.style.display = "none";
    }
  });
});
