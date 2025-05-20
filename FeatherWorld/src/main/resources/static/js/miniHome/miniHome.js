document.addEventListener('DOMContentLoaded', () => {
  // 댓글 관련 요소
  const writeBtn = document.getElementById('write-button');
  const commentForm = document.getElementById('comment-form');
  const submitBtn = document.getElementById('submit-comment');
  const commentInput = document.getElementById('comment-input');
  const commentList = document.getElementById('comment-list');

  // 프로필 관련 (현재 HTML에는 bio, profile 버튼 없으니 프로필 부분 주석 처리 가능)
  const bioInput = document.getElementById("bio"); // 없으면 null
  const bioDisplay = document.getElementById("bio-display");
  const bioButton = document.querySelector(".left-sidebar .Board-button");
  const confirmEditBtn = document.getElementById("confirmEditBtn");
  const backBtn = document.getElementById("backToProfileBtn");
  const profileUpdateBtn = document.getElementById("profileUpdateBtn");
  const deleteAccountBtn = document.getElementById("deleteAccountBtn");
  
  // 이미지 업로드 및 미리보기 관련
  const multiImageInput = document.getElementById("multiImageInput");
  const imagePreviewRow = document.getElementById("imagePreviewRow");

  // --- 댓글 기능 ---

  writeBtn?.addEventListener('click', () => {
    if (commentForm.style.display === 'none' || commentForm.style.display === '') {
      commentForm.style.display = 'block';
      commentInput.focus();
    } else {
      commentForm.style.display = 'none';
    }
  });

  submitBtn?.addEventListener('click', () => {
    const commentText = commentInput.value.trim();
    if (!commentText) {
      alert('댓글을 입력해주세요!');
      return;
    }

    const newComment = document.createElement('div');
    newComment.classList.add('comment-item');
    newComment.innerHTML = `
      <img src="/images/user.png" alt="User avatar" class="avatar" />
      <div class="comment-content">
        <span class="username">익명</span>
        <span class="comment-text">${commentText}</span>
        <div class="comment-meta">
          <span class="user-role">Newbie</span>
          <button class="delete-btn">Delete</button>
        </div>
      </div>
    `;

    const hr = document.createElement('hr');

    commentList.appendChild(newComment);
    commentList.appendChild(hr);

    newComment.querySelector('.delete-btn').addEventListener('click', () => {
      newComment.remove();
      hr.remove();
    });

    commentInput.value = '';
    commentForm.style.display = 'none';
  });

  document.querySelectorAll('.delete-btn').forEach((btn) => {
    btn.addEventListener('click', (e) => {
      const commentItem = e.target.closest('.comment-item');
      if (commentItem) {
        const next = commentItem.nextElementSibling;
        if (next && next.tagName === 'HR') next.remove();
        commentItem.remove();
      }
    });
  });

  // --- 이미지 다중 업로드 미리보기 (최대 5장) ---
  multiImageInput?.addEventListener("change", () => {
    imagePreviewRow.innerHTML = "";

    const files = Array.from(multiImageInput.files).slice(0, 5);

    files.forEach((file, index) => {
      const reader = new FileReader();

      reader.onload = (e) => {
        const img = document.createElement("img");
        img.src = e.target.result;
        img.alt = `Uploaded Image ${index + 1}`;
        img.style.cursor = "pointer";
        img.style.width = "80px";  // 필요 시 조정
        img.style.height = "80px";
        img.style.objectFit = "cover";
        img.style.marginRight = "5px";
        img.title = "클릭 시 이미지 재선택 가능";

        // 클릭 시 경고 (간단 구현)
        img.addEventListener("click", () => {
          alert("이미지를 삭제하려면 다시 파일을 선택해주세요.");
        });

        imagePreviewRow.appendChild(img);
      };

      reader.readAsDataURL(file);
    });
  });

  // --- 프로필 수정 관련 (HTML에 버튼/필드 없으면 생략 가능) ---
  confirmEditBtn?.addEventListener("click", async () => {
    if (!bioInput) return; // bioInput 없으면 무시

    const bioText = bioInput.value.trim();
    if (!bioText) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    const formData = new FormData();

    const files = multiImageInput.files;
    for (let i = 0; i < files.length && i < 5; i++) {
      formData.append("images", files[i]);
    }
    formData.append("bio", bioText);

    try {
      const response = await fetch("/profile/update", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        alert("프로필이 성공적으로 업데이트되었습니다.");
        window.location.href = "/profile";
      } else {
        alert("프로필 업데이트에 실패했습니다.");
      }
    } catch (error) {
      alert("서버 오류가 발생했습니다.");
      console.error(error);
    }
  });

  backBtn?.addEventListener("click", () => {
    window.location.href = "/profile";
  });

  profileUpdateBtn?.addEventListener("click", () => {
    window.location.href = "/profile/edit";
  });

  deleteAccountBtn?.addEventListener("click", () => {
    window.location.href = "/account/delete";
  });
});
