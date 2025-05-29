// 프로필 수정 모드 상태 변수
let isEditMode = false;
let selectedImageFile = null;
let originalBioText = "";

// 일촌 신청 함수
function follow(toMemberNo) {
  if (!confirm("이 분에게 일촌 신청을 보내시겠습니까?")) {
    return;
  }

  const followBtn = document.getElementById("profileFollowBtn");
  if (followBtn) {
    followBtn.disabled = true;
    followBtn.textContent = "신청 중...";
  }

  fetch(`/${toMemberNo}/follow`, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      //application/x-www-form-urlencoded    / toMemberNo=8   / HTML폼, 간단한 데이터
      // json은 js객체형이라 맵같은 key:value 값이 와야되는데 이건 좀 간단하게 쓰깁편함
      //  HTML 데이터 RequestParam으로 단일데이터만 필요할때는 이런식으로 요청해서 받을수잇음
      // 근데 .. json이 더좋은것같기도 ?!
    },
    body: "toMemberNo=" + toMemberNo,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("네트워크 오류: " + response.status);
      }
      return response.json();
    })
    .then((data) => {
      if (data.success) {
        alert("✅" + data.message); //유니코드 이모지 ㅋㅋ
        if (followBtn) {
          followBtn.textContent = "신청됨";
          followBtn.style.backgroundColor = "#6c757d";
          followBtn.disabled = true;
        }
      } else {
        alert("❌ " + data.message); //유니코드 이모지 ㅋㅋ
        if (followBtn) {
          followBtn.disabled = false;
          followBtn.textContent = "Follow";
        }
      }
    });
}

// 미수락 팔로워 신청 보기
function showPendingFollowers() {
  alert(
    "새로운 일촌 신청이 있습니다!\n일촌 신청 목록 페이지로 이동하시겠습니까?"
  );
  window.location.href = `/${memberNo}/friendList/incoming`; // 일촌 신청 목록 페이지로 이동
}

// 프로필 수정 모드 토글 (기존 editProfile 함수 대체)
function editProfile() {
  if (!isEditMode) {
    // 수정 모드 시작
    enterEditMode();
  } else {
    // 수정 모드 종료 확인
    if (confirm("프로필 수정을 저장하시겠습니까?")) {
      saveProfileChanges();
    } else {
      cancelEdit();
    }
  }
}

// 수정 모드 진입
function enterEditMode() {
  isEditMode = true;

  // Edit Profile 버튼 텍스트 변경
  const editBtn = document.getElementById("profileEditBtn");
  if (editBtn) {
    editBtn.textContent = "Save";
    editBtn.style.backgroundColor = "#28a745"; // 초록색으로 변경
  }

  // 프로필 이미지 edit 아이콘 표시
  const editIcon = document.getElementById("profileImageEditIcon");
  if (editIcon) {
    editIcon.style.display = "block";
  }

  // 취소 버튼 표시 (만약 있다면)
  const cancelBtn = document.getElementById("profileCancelBtn");
  if (cancelBtn) {
    cancelBtn.style.display = "inline-block";
  }

  // 다른 수정 가능한 요소들 활성화
  enableEditableFields();

  console.log("프로필 수정 모드 활성화");
}

// 수정 모드 종료
function exitEditMode() {
  isEditMode = false;

  // Edit Profile 버튼 원래대로
  const editBtn = document.getElementById("profileEditBtn");
  if (editBtn) {
    editBtn.textContent = "Edit Profile";
    editBtn.style.backgroundColor = "#9f2120"; // 원래 색상으로
  }

  // 프로필 이미지 edit 아이콘 숨기기
  const editIcon = document.getElementById("profileImageEditIcon");
  if (editIcon) {
    editIcon.style.display = "none";
  }

  // 취소 버튼 숨기기
  const cancelBtn = document.getElementById("profileCancelBtn");
  if (cancelBtn) {
    cancelBtn.style.display = "none";
  }

  // 수정 가능한 요소들 비활성화
  disableEditableFields();

  console.log("프로필 수정 모드 비활성화");
}

// 수정 가능한 필드들 활성화
function enableEditableFields() {
  // Bio 텍스트를 textarea로 변경하거나 편집 가능하게 만들기
  const bioDisplay = document.getElementById("profileBioDisplay");
  if (bioDisplay) {
    bioDisplay.contentEditable = true;
    bioDisplay.style.border = "1px dashed #9f2120";
    bioDisplay.style.padding = "8px";
    bioDisplay.style.borderRadius = "4px";
  }
}

// 수정 가능한 필드들 비활성화
function disableEditableFields() {
  const bioDisplay = document.getElementById("profileBioDisplay");
  if (bioDisplay) {
    bioDisplay.contentEditable = false;
    bioDisplay.style.border = "none";
    bioDisplay.style.padding = "12px";
  }
}

// 프로필 변경사항 저장
function saveProfileChanges() {
  // FormData 객체 생성
  const formData = new FormData();

  // bio 내용 가져오기
  const bioDisplay = document.getElementById("profileBioDisplay");
  if (bioDisplay) {
    const bioText = bioDisplay.textContent || bioDisplay.innerText || "";
    formData.append("memberIntro", bioText.trim());
  }

  // 이미지 파일이 선택되었으면 추가
  if (selectedImageFile) {
    formData.append("memberImg", selectedImageFile);
    console.log("이미지 파일 추가:", selectedImageFile.name);
  }

  // 서버로 전송
  fetch(`/${memberNo}/leftProfileUpdate`, {
    method: "POST",
    body: formData,
    credentials: "same-origin", // 세션 쿠키 포함
  })
    .then((response) => {
      // 응답 상태 체크
      if (!response.ok) {
        throw new Error("네트워크 오류: " + response.status);
      }
      return response.json(); // JSON으로 변환
    })
    .then((data) => {
      // 서버 응답 처리
      console.log("서버 응답:", data);

      if (data.success) {
        alert("✅ " + data.message);
        exitEditMode();
        // 페이지 새로고침하여 업데이트된 정보 반영
        location.reload();
      } else {
        alert("❌ " + data.message);
      }
    })
    .catch((error) => {
      // 에러 처리
      console.error("프로필 업데이트 오류:", error);
      alert("❌ 프로필 업데이트 중 오류가 발생했습니다.");
    });
}

// 프로필 수정 취소 (기존 cancelEdit 함수 개선)
function cancelEdit() {
  if (confirm("수정사항을 취소하시겠습니까?")) {
    // 원래 데이터로 복원
    restoreOriginalData();
    exitEditMode();
  }
}

// 원래 데이터 복원
function restoreOriginalData() {
  // 수정된 내용을 원래대로 되돌리기
  const bioDisplay = document.getElementById("profileBioDisplay");
  if (bioDisplay) {
    // 원래 bio 내용으로 복원 (서버에서 가져오거나 저장된 값 사용)
    bioDisplay.textContent = "자기소개가 없습니다."; // 예시
  }
}

// 프로필 이미지 수정 (기존 함수 개선)
function editProfileImage() {
  if (!isEditMode) {
    alert("프로필 수정 모드에서만 이미지를 변경할 수 있습니다.");
    return;
  }

  // 파일 선택 다이얼로그 열기
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = "image/*";
  fileInput.onchange = function (event) {
    const file = event.target.files[0];
    if (file) {
      // 이미지 미리보기
      selectedImageFile = file;
      const reader = new FileReader();
      reader.onload = function (e) {
        const profileImg = document.querySelector("#profileMainImage");
        if (profileImg) {
          profileImg.src = e.target.result;
        }
      };
      reader.readAsDataURL(file);

      // 실제 업로드는 Save 버튼 클릭 시 처리
      console.log("이미지 변경 예정:", file.name);
      console.log("selectedImageFile 설정됨:", selectedImageFile);
    }
  };
  fileInput.click();
}

// 로그아웃
function logout() {
  if (confirm("로그아웃 하시겠습니까?")) {
    fetch("/member/logout", {
      method: "POST",
      credentials: "same-origin",
      //  credentials: "same-origin",  쿠키/세션 정보 함께 전송  >> 서버에서 로그인된 사용자 확인 가능
      // 그래서 이걸씀 .. 개꿀이네
      // 사실 우리가 쿠키에 저장되는 카카오 로그인값들은 어차피 처리안됌 ..
    })
      .then(() => {
        window.location.href = "/";
      })
      .catch((error) => {
        console.error("로그아웃 오류:", error);
        // 에러가 발생해도 메인 페이지로 이동
        window.location.href = "/";
      });
  }
}

// 서핑 (랜덤 미니홈 방문)
function goSurfing() {
  
  if (confirm("랜덤으로 다른 사람의 미니홈을 방문하시겠습니까?")) {
    // 서버에서 랜덤 회원 번호를 받아와서 이동
    // 모든 회원 에대한 회원번호를 랜덤으로받아오기
    fetch("/surfing")
      .then((response) => response.text())
      .then((data) => {
        if (data !== 0) {
          window.location.href = "/" + data + "/minihome";
        } else {
          alert("현재 방문할 수 있는 미니홈이 없습니다.");
        }
      })
      .catch((error) => {
        console.error("서핑 오류:", error);
        alert("서핑 중 오류가 발생했습니다.");
      });
  }
}

// 프로필 수정 모드 관련 함수들 (기존 함수 개선)
function confirmEdit() {
  // saveProfileChanges()로 통합됨
  saveProfileChanges();
}

// DOM 로드 완료 시 초기화
document.addEventListener("DOMContentLoaded", function () {
  console.log("MiniHome JavaScript 로드 완료");

  // 프로필 이미지 edit 아이콘 초기에 숨기기
  const editIcon = document.getElementById("profileImageEditIcon");
  if (editIcon) {
    editIcon.style.display = "none";
  }
});
