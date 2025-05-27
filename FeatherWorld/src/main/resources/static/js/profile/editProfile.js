// 요소 선택
const imageInput = document.getElementById("uploadFile");
const bioInput = document.getElementById("bio-input");
const previewImage = document.getElementById("preview");
const bioDisplay = document.getElementById("bio-display");
const bioButton = document.querySelector(".left-sidebar .Board-button");

const confirmEditBtn = document.getElementById("confirmEditBtn");
const backBtn = document.getElementById("backToProfileBtn");
const profileUpdateBtn = document.getElementById("profileUpdateBtn");
const deleteAccountBtn = document.getElementById("deleteAccountBtn");
const saveBioBtn = document.getElementById("saveBioBtn");

// 서버에서 전달된 memberNo (필수!)
let memberNo;
try {
  memberNo = window.memberNo; // 전역에서 선언된 memberNo
  if (!memberNo) throw new Error();
} catch {
  console.error("⚠️ memberNo가 정의되지 않았습니다. Thymeleaf에서 전달 필요!");
}

let uploadedImageData = null;

// 이미지 미리보기 처리
if (imageInput) {
  imageInput.addEventListener("change", () => {
    const file = imageInput.files[0];
    if (!file) {
      previewImage.style.display = "none";
      uploadedImageData = null;
      return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
      previewImage.src = e.target.result;
      previewImage.style.display = "block";
      uploadedImageData = e.target.result;
    };
    reader.readAsDataURL(file);
  });
}

// 이미지 클릭 시 다시 업로드 가능하도록
if (previewImage) {
  previewImage.addEventListener("click", () => {
    previewImage.style.display = "none";
    imageInput.value = "";
    uploadedImageData = null;
  });
}

// Bio 실시간 반영
if (bioInput && bioDisplay) {
  bioInput.addEventListener("input", () => {
    const text = bioInput.value.trim();
    bioDisplay.textContent = text;
    if (bioButton) {
      bioButton.textContent = text.length > 0 ? text : "Bio";
    }
  });
}

// Bio 저장 버튼 클릭 시 (단독 Bio 저장용)
if (saveBioBtn) {
  saveBioBtn.addEventListener("click", () => {
    const bio = bioInput.value.trim();
    if (!bio) return alert("Bio를 입력하세요.");

    fetch("/profile/updateBio", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ bio }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("서버 오류");
        return res.text();
      })
      .then(() => alert("Bio가 저장되었습니다."))
      .catch((err) => alert(err.message));
  });
}

// 프로필 저장 (확인 버튼)
if (confirmEditBtn) {
  confirmEditBtn.addEventListener("click", async () => {
    const bioText = bioInput.value.trim();

    if (!bioText) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    const formData = new FormData();
    if (imageInput.files[0]) {
      formData.append("uploadFile", imageInput.files[0]);
    }
    formData.append("bio", bioText);

    try {
      const response = await fetch(`/${memberNo}/profileupdate`, {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        alert("프로필이 성공적으로 업데이트되었습니다.");
        window.location.href = `/${memberNo}/profileupdate`;
      } else {
        alert("프로필 업데이트에 실패했습니다.");
      }
    } catch (error) {
      alert("서버 오류가 발생했습니다.");
      console.error(error);
    }
  });
}

// 뒤로가기
if (backBtn) {
  backBtn.addEventListener("click", () => {
    window.location.href = `/${memberNo}/profile`;
  });
}

// 수정 페이지 이동
if (profileUpdateBtn) {
  profileUpdateBtn.addEventListener("click", () => {
    window.location.href = `/${memberNo}/profileupdate`;
  });
}

// 계정 삭제 페이지 이동
if (deleteAccountBtn) {
  deleteAccountBtn.addEventListener("click", () => {
    window.location.href = "/account/delete";
  });
}
