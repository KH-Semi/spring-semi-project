const imageInput = document.getElementById("image");
const bioInput = document.getElementById("bio-input");
const previewImage = document.getElementById("previewImage");
const bioDisplay = document.getElementById("bio-display");
const bioButton = document.querySelector(".left-sidebar .Board-button");

const confirmEditBtn = document.getElementById("confirmEditBtn");
const backBtn = document.getElementById("backToProfileBtn");
const profileUpdateBtn = document.getElementById("profileUpdateBtn");
const deleteAccountBtn = document.getElementById("deleteAccountBtn");

let uploadedImageData = null;

// 이미지 미리보기
imageInput.addEventListener("change", () => {
  const file = imageInput.files[0];
  if (!file) {
    previewImage.style.display = "none";
    imageInput.style.display = "block";
    uploadedImageData = null;
    return;
  }

  const reader = new FileReader();
  reader.onload = function (e) {
    previewImage.src = e.target.result;
    previewImage.style.display = "block";
    imageInput.style.display = "none";
    uploadedImageData = e.target.result;
  };
  reader.readAsDataURL(file);
});

// 미리보기 클릭 시 다시 파일 선택
previewImage.addEventListener("click", () => {
  previewImage.style.display = "none";
  imageInput.value = "";
  imageInput.style.display = "block";
  uploadedImageData = null;
});

// Bio 텍스트 실시간 반영
bioInput.addEventListener("input", () => {
  const text = bioInput.value.trim();
  bioDisplay.textContent = text;
  bioButton.textContent = text.length > 0 ? text : "Bio";
});

// 확인(수정 반영) 버튼 (페이지 내 bio와 이미지 업데이트용)
if (confirmEditBtn) {
  confirmEditBtn.addEventListener("click", () => {
    const bioText = bioInput.value.trim();

    if (!bioText) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    bioDisplay.textContent = bioText;

    if (uploadedImageData) {
      previewImage.style.display = "block";
    }

    alert("수정이 반영되었습니다!");
  });
}

// 뒤로 가기 버튼
if (backBtn) {
  backBtn.addEventListener("click", () => {
    window.location.href = "/profile";
  });
}

// 프로필 업데이트 페이지 이동
if (profileUpdateBtn) {
  profileUpdateBtn.addEventListener("click", () => {
    window.location.href = "/profile/edit";
  });
}

// 계정 삭제 페이지 이동
if (deleteAccountBtn) {
  deleteAccountBtn.addEventListener("click", () => {
    window.location.href = "/account/delete";
  });
}

confirmEditBtn.addEventListener("click", async () => {
  const bioText = bioInput.value.trim();
  if (!bioText) {
    alert("자기소개를 입력해주세요.");
    return;
  }

  // 폼 데이터 준비 (이미지 + bio)
  const formData = new FormData();
  if (imageInput.files[0]) {
    formData.append("image", imageInput.files[0]);
  }
  formData.append("bio", bioText);

  try {
    const response = await fetch("/profile/update", {
      method: "POST",
      body: formData,
    });

    if (response.ok) {
      alert("프로필이 성공적으로 업데이트되었습니다.");
      // 업데이트 후 프로필 페이지로 이동
      window.location.href = "/profile";
    } else {
      alert("프로필 업데이트에 실패했습니다.");
    }
  } catch (error) {
    alert("서버 오류가 발생했습니다.");
    console.error(error);
  }
});
