// edit 버튼
const editProfileBtn = document.getElementById("editProfileBtn");

editProfileBtn.addEventListener("click", () => {
  window.location.href = "/profile/edit"; // 원하는 경로로 수정 가능
});

// 로그아웃 버튼
const logoutBtn = document.getElementById("logoutBtn");
if (logoutBtn) {
  logoutBtn.addEventListener("click", () => {
    window.location.href = "/logout"; // 로그아웃 처리 URL
  });
}

// 프로필 업데이트 버튼
const profileUpdateBtn = document.getElementById("profileUpdateBtn");
if (profileUpdateBtn) {
  profileUpdateBtn.addEventListener("click", () => {
    window.location.href = "/profile/edit"; // 프로필 편집 페이지 경로
  });
}

// 계정 삭제 버튼
const deleteAccountBtn = document.getElementById("deleteAccountBtn");
if (deleteAccountBtn) {
  deleteAccountBtn.addEventListener("click", () => {
    window.location.href = "/account/delete"; // 계정 삭제 페이지 경로
  });
}
