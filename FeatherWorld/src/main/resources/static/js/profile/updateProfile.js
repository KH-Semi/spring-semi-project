document.addEventListener("DOMContentLoaded", () => {
  const imageInput = document.getElementById("image");
  const previewImage = document.getElementById("previewImage");
  const previewContainer = document.getElementById("previewContainer");
  const bioInput = document.getElementById("bio-input");
  const bioDisplay = document.getElementById("bio-display");
  const confirmBtn = document.getElementById("confirmBtn");
  const backBtn = document.getElementById("backToProfileBtn");
  const uploadForm = document.getElementById("uploadForm");

  // 이미지 선택하면 미리보기 표시
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

  // 자기소개 입력 시 미리보기 텍스트 반영
  bioInput.addEventListener("input", () => {
    const text = bioInput.value.trim();
    bioDisplay.textContent = text;
  });

  // Confirm 버튼 클릭 시 폼 제출 전 간단 검증
  confirmBtn.addEventListener("click", (e) => {
    e.preventDefault();

    // 예시: 자기소개가 없고 이미지도 없으면 alert
    const bioText = bioInput.value.trim();
    const hasImage = imageInput.files.length > 0;

    if (!bioText && !hasImage) {
      alert("자기소개나 이미지를 입력해주세요.");
      return;
    }

    // 필요한 경우 추가 검증 가능

    // 폼 제출 (서버에 업로드)
    uploadForm.submit();
  });

  // Back 버튼 클릭 시 이전 페이지로 이동
  backBtn.addEventListener("click", (e) => {
    e.preventDefault();
    history.back();
  });
});
