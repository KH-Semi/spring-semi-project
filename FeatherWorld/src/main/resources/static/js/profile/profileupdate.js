document.addEventListener("DOMContentLoaded", () => {
  const imageInput = document.getElementById("image");
  const bioInput = document.getElementById("bio-input");
  const confirmBtn = document.querySelector(".confirm-button");
  const backBtn = document.getElementById("backToProfileBtn");
  const uploadForm = document.getElementById("uploadForm");

  // 업로드된 이미지 미리보기 요소 (존재할 경우에만)
  const previewImage = document.querySelector(".image-upload-box img");

  // 이미지 선택 시 미리보기 반영
  imageInput.addEventListener("change", () => {
    const file = imageInput.files[0];
    if (file && previewImage) {
      const reader = new FileReader();
      reader.onload = (e) => {
        previewImage.src = e.target.result;
        previewImage.style.display = "block";
      };
      reader.readAsDataURL(file);
    }
  });

  // Confirm 버튼 클릭 시 검증 및 제출
  confirmBtn.addEventListener("click", (e) => {
    e.preventDefault();
    if (!bioInput.value.trim() && imageInput.files.length === 0) {
      alert("자기소개나 이미지를 입력해주세요.");
      return;
    }
    uploadForm.submit();
  });

  // Back 버튼 클릭 시 프로필 페이지로 이동
  backBtn.addEventListener("click", (e) => {
    e.preventDefault();
    location.href = `/${memberNo}/profile`;
  });

  // 폼 제출 시에도 검증
  uploadForm.addEventListener("submit", (e) => {
    if (!bioInput.value.trim() && imageInput.files.length === 0) {
      e.preventDefault();
      alert("자기소개나 이미지를 입력해주세요.");
    }
  });
});
