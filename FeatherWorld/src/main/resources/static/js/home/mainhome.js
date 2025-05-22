document.addEventListener("DOMContentLoaded", function () {
  // DOM 요소 선택
  const searchInput = document.querySelector(".member-search");
  const searchResults = document.getElementById("searchResults");
  const closeBtn = document.getElementById("closeBtn");
  const kakaoLoginBtn = document.querySelector(".kakao-btn");
  const logoutBtn = document.querySelector(".logout-btn");
  const profileImg = document.querySelector(".profile-avatar img");
  // Kakao SDK 초기화 확인 및 재시도

  if (profileImg != null) {
    profileImg.addEventListener("click", () => {
      if (memberNo) {
        window.location.href = `/${memberNo}/updateMember`; // 단순하게 GET 요청
      }
    });
  }
  function ensureKakaoInit() {
    if (typeof Kakao !== "undefined") {
      // 아직 초기화되지 않았다면 초기화 실행
      if (!Kakao.isInitialized()) {
        try {
          Kakao.init("e03376ec020087e66ba936c86bceebe2");
          console.log("Kakao SDK 초기화 상태:", Kakao.isInitialized());
        } catch (e) {
          console.error("Kakao SDK 초기화 오류:", e);
          return false;
        }
      }
      return Kakao.isInitialized();
    } else {
      console.warn("Kakao SDK가 로드되지 않았습니다.");
      return false;
    }
  }

  // 로그아웃 버튼 이벤트
  if (logoutBtn) {
    logoutBtn.addEventListener("click", function () {
      console.log("로그아웃 버튼 클릭됨");

      // 간단한 POST 요청
      fetch("/member/logout", {
        method: "POST",
        credentials: "same-origin",
      }).then(() => {
        // 성공 여부와 상관없이 페이지 새로고침
        window.location.href = "/";
      });
    });
  }
  // 카카오 로그인 함수
  function kakaoLogin() {
    try {
      // SDK가 로드 및 초기화되었는지 확인
      if (!ensureKakaoInit()) {
        alert("카카오 SDK를 불러오지 못했습니다. 페이지를 새로고침해 주세요.");
        return;
      }

      // 기존 인증 상태 초기화 시도 (오류가 발생해도 계속 진행)

      // 카카오 로그인 실행 - 웹 기반 로그인 강제 사용
      Kakao.Auth.login({
        throughTalk: false, // 카카오톡 앱 사용 안함, 항상 웹 로그인 페이지 사용
        scope: "profile_nickname account_email",
        success: function (authObj) {
          console.log("카카오 인증 성공:", authObj);

          Kakao.API.request({
            url: "/v2/user/me",
            success: function (res) {
              const kakao_account = res.kakao_account || {};
              console.log("카카오 사용자 정보:", kakao_account);

              // 프로필 정보 안전하게 접근
              const memberEmail = kakao_account.email || "";
              const memberName =
                kakao_account.profile?.nickname || "카카오 사용자";

              fetch("/member/kakaoLogin", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                  memberEmail: memberEmail,
                  memberName: memberName,
                  kakaoToken: authObj.access_token,
                }),
              })
                .then((response) => {
                  if (!response.ok) {
                    throw new Error("서버 응답 오류: " + response.status);
                  }
                  return response.json();
                })
                .then((data) => {
                  // 서버 응답 처리
                  if (data.success) {
                    console.log("카카오 로그인 성공:", data.message);

                    // 새 회원이면 추가 정보 입력 페이지로, 아니면 메인으로 리다이렉트
                    if (data.isNewMember) {
                      alert("환영합니다! 회원가입이 완료되었습니다.");
                    }

                    // 로그인 성공 시 메인 페이지로 리다이렉트
                    window.location.href = "/";
                  } else {
                    // 로그인 실패 처리
                    console.error("로그인 실패:", data.message);
                    alert("로그인에 실패했습니다: " + data.message);
                  }
                });
            },
            fail: function (error) {
              console.error("카카오 사용자 정보 요청 실패:", error);
              alert("카카오 계정 정보를 가져오는데 실패했습니다.");
            },
          });
        },
        fail: function (error) {
          console.error("카카오 로그인 실패:", error);
          alert(
            "카카오 로그인에 실패했습니다. 오류 코드: " +
              (error.error_code || "알 수 없음")
          );
        },
      });
    } catch (error) {
      console.error("카카오 로그인 실행 중 오류:", error);
      alert(
        "카카오 로그인 중 오류가 발생했습니다: " +
          (error.message || "알 수 없는 오류")
      );
    }
  }

  // 카카오 로그인 버튼 이벤트
  if (kakaoLoginBtn) {
    kakaoLoginBtn.addEventListener("click", function () {
      // 로그인 실행
      kakaoLogin();
    });
  }

  // 검색 결과 표시 함수
  function displayResults(members) {
    if (!searchResults) return;

    searchResults.innerHTML = ""; // 이전 결과 비우기

    if (!members || members.length === 0) {
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
          member.memberName || "사용자"
        }">
            </div>
            <div class="member-result-info">
              <div class="member-result-name">${
                member.memberName || "사용자"
              }</div>
              <div class="member-result-email">${member.memberEmail || ""}</div>
            </div>
          </div>
        `;

        // 회원 클릭 시 해당 미니홈으로 이동
        li.addEventListener("click", () => {
          if (member.memberNo) {
            window.location.href = `/${member.memberNo}/minihome`;
          }
        });

        searchResults.appendChild(li);
      });
    }

    searchResults.style.display = "block";
  }

  // 검색 입력에 이벤트 리스너 추가 - 즉시 검색 실행
  if (searchInput) {
    searchInput.addEventListener("input", function () {
      const searchTerm = this.value.trim();

      // 입력 값이 없으면 결과와 닫기 버튼 숨김
      if (searchTerm === "") {
        if (searchResults) searchResults.style.display = "none";
        if (closeBtn) closeBtn.style.display = "none";
        return;
      }

      // 검색어가 있으면 닫기 버튼 표시
      if (closeBtn) closeBtn.style.display = "inline";

      // 즉시 검색 실행
      fetch(`/member/search?memberName=${encodeURIComponent(searchTerm)}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("검색 요청 실패: " + response.status);
          }
          return response.json();
        })
        .then((data) => {
          // 검색 결과 표시
          displayResults(data);
        })
        .catch((error) => {
          console.error("검색 중 오류 발생:", error);
          if (searchResults) {
            searchResults.innerHTML = "<li>검색 중 오류가 발생했습니다.</li>";
            searchResults.style.display = "block";
          }
        });
    });
  }

  // 닫기 버튼 클릭 이벤트
  if (closeBtn) {
    closeBtn.addEventListener("click", function () {
      if (searchInput) searchInput.value = ""; // 입력 내용 지우기
      if (searchResults) searchResults.style.display = "none"; // 결과 숨기기
      closeBtn.style.display = "none"; // 닫기 버튼 숨기기
    });
  }

  // 검색창 외부 클릭 시 결과 숨기기
  document.addEventListener("click", function (event) {
    if (
      searchInput &&
      searchResults &&
      closeBtn &&
      !searchInput.contains(event.target) &&
      !searchResults.contains(event.target) &&
      event.target !== closeBtn
    ) {
      searchResults.style.display = "none";
    }
  });
});
