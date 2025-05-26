// 요소 선택
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

// 이미지 미리보기 처리
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

// 미리보기 이미지 클릭 시 파일 선택창으로 돌아가기
previewImage.addEventListener("click", () => {
  previewImage.style.display = "none";
  imageInput.value = "";
  imageInput.style.display = "block";
  uploadedImageData = null;
});

// Bio 입력 실시간 반영
bioInput.addEventListener("input", () => {
  const text = bioInput.value.trim();
  bioDisplay.textContent = text;
  if (bioButton) {
    bioButton.textContent = text.length > 0 ? text : "Bio";
  }
});

document.getElementById("saveBioBtn").addEventListener("click", () => {
  const bio = document.getElementById("bioInput").value.trim();
  if (!bio) return alert("Bio를 입력하세요.");

  fetch("/profile/updateBio", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ bio }),
  })
    .then((response) => {
      if (response.ok) return response.text();
      else throw new Error("서버 오류");
    })
    .then((result) => {
      alert("Bio가 저장되었습니다.");
    })
    .catch((err) => alert(err.message));
});

// 확인 버튼 클릭 이벤트 (통합 버전)
if (confirmEditBtn) {
  confirmEditBtn.addEventListener("click", async () => {
    const bioText = bioInput.value.trim();

    if (!bioText) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    // UI에 실시간 반영
    bioDisplay.textContent = bioText;
    if (bioButton) {
      bioButton.textContent = bioText.length > 0 ? bioText : "Bio";
    }

    if (uploadedImageData) {
      previewImage.style.display = "block";
      imageInput.style.display = "none";
    } else {
      previewImage.style.display = "none";
      imageInput.style.display = "block";
    }

    // 폼 데이터 구성
    const formData = new FormData();
    if (imageInput.files[0]) {
      formData.append("image", imageInput.files[0]);
    }
    formData.append("bio", bioText);

    try {
      const response = await fetch(`/${memberNo}/profileupdate`, {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        alert("프로필이 성공적으로 업데이트되었습니다.");
        window.location.href = `/${memberNo}/profile`;
      } else {
        alert("프로필 업데이트에 실패했습니다.");
      }
    } catch (error) {
      alert("서버 오류가 발생했습니다.");
      console.error(error);
    }
  });
}

// 뒤로 가기 버튼 클릭 이벤트
if (backBtn) {
  backBtn.addEventListener("click", () => {
    window.location.href = "/profile";
  });
}

// 프로필 업데이트 페이지 이동 버튼 클릭 이벤트
if (profileUpdateBtn) {
  profileUpdateBtn.addEventListener("click", () => {
    window.location.href = `/${memberNo}/profileupdate`; // ✅ memberNo 포함
  });
}

// 계정 삭제 페이지 이동 버튼 클릭 이벤트
if (deleteAccountBtn) {
  deleteAccountBtn.addEventListener("click", () => {
    window.location.href = "/account/delete";
  });
}
