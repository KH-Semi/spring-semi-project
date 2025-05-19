document.addEventListener('DOMContentLoaded', () => {
  const writeBtn = document.getElementById('write-button');
  const commentForm = document.getElementById('comment-form');
  const submitBtn = document.getElementById('submit-comment');
  const commentInput = document.getElementById('comment-input');
  const commentList = document.getElementById('comment-list');
// JS 코드
document.getElementById('editProfileBtn').addEventListener('click', () => {
  // 회원 번호: 숫자로 변환, 문자열이라면 parseInt 혹은 그냥 문자열도 됨
  const memberNo = document.getElementById('memberNo').textContent.trim();

  // 닉네임에서 '@' 제거 (원하면)
  const nicknameWithAt = document.getElementById('nickname').textContent.trim();
  const nickname = nicknameWithAt.startsWith('@') ? nicknameWithAt.slice(1) : nicknameWithAt;

  // bio textarea 값
  const bio = document.getElementById('bio').value.trim();

  // data 객체 생성
  const data = { memberNo, nickname, bio };

  console.log('저장할 데이터:', data);

  // 여기서 AJAX 요청으로 서버에 데이터 저장 가능
  /*
  fetch('/api/saveProfile', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  }).then(res => res.json())
    .then(result => {
      alert('프로필이 저장되었습니다!');
    }).catch(err => {
      alert('저장 중 오류가 발생했습니다.');
      console.error(err);
    });
  */
});

  // Write 버튼 클릭 시 댓글 입력창 토글
  writeBtn.addEventListener('click', () => {
    if (commentForm.style.display === 'none' || commentForm.style.display === '') {
      commentForm.style.display = 'block';
      commentInput.focus();
    } else {
      commentForm.style.display = 'none';
    }
  });

  // 댓글 등록 버튼 클릭 시
  submitBtn.addEventListener('click', () => {
    const commentText = commentInput.value.trim();
    if (!commentText) {
      alert('댓글을 입력해주세요!');
      return;
    }

    // 새 댓글 요소 생성
    const newComment = document.createElement('div');
    newComment.classList.add('comment-item');
    newComment.innerHTML = `
      <img src="/FeatherWorld/src/main/resources/static/images/user.png" alt="User avatar" class="avatar" />
      <div class="comment-content">
        <span class="username">익명</span>
        <span class="comment-text">${commentText}</span>
        <div class="comment-meta">
          <span class="user-role">Newbie</span>
          <button class="delete-btn">Delete</button>
        </div>
      </div>
    `;

    // 구분선 <hr> 추가
    const hr = document.createElement('hr');

    // 댓글 추가
    commentList.appendChild(newComment);
    commentList.appendChild(hr);

    // 삭제 버튼에 이벤트 연결
    newComment.querySelector('.delete-btn').addEventListener('click', () => {
      newComment.remove();
      hr.remove();
    });

    // 입력창 초기화 및 숨김
    commentInput.value = '';
    commentForm.style.display = 'none';
  });

  // 기존 댓글 삭제 기능 연결
  document.querySelectorAll('.delete-btn').forEach((btn) => {
    btn.addEventListener('click', (e) => {
      const commentItem = e.target.closest('.comment-item');
      if (commentItem) {
        // 댓글 다음 요소가 <hr>이면 같이 제거
        const next = commentItem.nextElementSibling;
        if (next && next.tagName === 'HR') {
          next.remove();
        }
        commentItem.remove();
      }
    });
  });
});

