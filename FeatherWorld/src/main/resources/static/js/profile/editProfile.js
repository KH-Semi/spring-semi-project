const imageInput = document.getElementById("image");
const bioInput = document.getElementById("bio-input");
const previewImage = document.getElementById("previewImage");
const bioDisplay = document.getElementById("bio-display");
const bioButton = document.querySelector(".left-panel .Board-button");

const confirmEditBtn = document.getElementById("confirmEditBtn");
const confirmBtn = document.getElementById("confirmBtn");
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

// 확인(수정 반영) 버튼
if (confirmEditBtn) {
  confirmEditBtn.addEventListener("click", () => {
    const bioText = bioInput.value.trim();
    const imageSrc = previewImage.src;

    if (!bioText) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    bioDisplay.textContent = bioText;

    if (imageSrc && imageSrc !== window.location.href) {
      previewImage.style.display = "block";
    }

    alert("수정이 반영되었습니다!");
  });
}

// 확인 후 서버 전송 (API)
if (confirmBtn) {
  confirmBtn.addEventListener("click", async () => {
    const bioText = bioInput.value.trim();
    if (bioText.length === 0) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    try {
      const response = await fetch("/api/updateBio", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ bio: bioText }),
      });

      if (response.ok) {
        alert("변경사항이 적용되었습니다!");
        window.location.href = "/profile";
      } else {
        alert("변경사항 적용에 실패했습니다. 다시 시도해주세요.");
      }
    } catch (error) {
      alert("서버와 통신 중 오류가 발생했습니다.");
      console.error(error);
    }
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
