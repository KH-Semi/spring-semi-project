document.addEventListener("DOMContentLoaded", () => {
  const editBtn = document.querySelector(".Edit-button");
  const updateBtn = document.querySelector(".Profile-update-button");
  const deleteBtn = document.querySelector(".Delete-account-button");

  if (editBtn) {
    editBtn.addEventListener("click", () => {
      window.location.href = "/edit-profile"; // 실제 URL로 수정
    });
  }

  if (updateBtn) {
    updateBtn.addEventListener("click", () => {
      window.location.href = "/profile-update"; // 실제 URL로 수정
    });
  }

  if (deleteBtn) {
    deleteBtn.addEventListener("click", () => {
      window.location.href = "/delete-account"; // 실제 URL로 수정
    });
  }
});
