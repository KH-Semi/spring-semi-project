document.addEventListener("DOMContentLoaded", () => {
  const imageInput = document.getElementById("image");
  const previewImage = document.getElementById("previewImage");
  const bioInput = document.getElementById("bio-input");
  const bioDisplay = document.getElementById("bio-display");
  const confirmBtn = document.getElementById("confirmBtn");
  const backBtn = document.getElementById("backToProfileBtn");
  const uploadForm = document.getElementById("uploadForm");

  // 이미지 선택 시 미리보기
  imageInput.addEventListener("change", () => {
    const file = imageInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        previewImage.src = e.target.result;
        previewImage.style.display = "block";
      };
      reader.readAsDataURL(file);
    } else {
      previewImage.src = "";
      previewImage.style.display = "none";
    }
  });

  // 자기소개 입력 시 미리보기 반영
  bioInput.addEventListener("input", () => {
    bioDisplay.textContent = bioInput.value.trim();
  });

  // Confirm 버튼 클릭 시 간단 검증 후 제출
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

  // 폼 제출 전 검증 (추가로 필요하면)
  uploadForm.addEventListener("submit", (e) => {
    if (!bioInput.value.trim() && imageInput.files.length === 0) {
      e.preventDefault();
      alert("자기소개나 이미지를 입력해주세요.");
    }
  });
});
