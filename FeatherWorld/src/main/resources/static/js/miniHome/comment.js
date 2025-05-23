 // 로그인 회원 번호와 게시글 주인 번호 (서버에서 Thymeleaf 등으로 넣기)
  const loginMemberNo = /*[[${loginMember != null ? loginMember.memberNo : 0}]]*/ 0;
  const targetMemberNo = /*[[${boardOwnerNo}]]*/ 0;

  // 일촌 여부 검사 후 버튼 노출
  fetch(`/comments/isIlchon?loginMemberNo=${loginMemberNo}&targetMemberNo=${targetMemberNo}`)
    .then(res => res.json())
    .then(isIlchon => {
      if (isIlchon) {
        const showBtn = document.getElementById('showCommentAreaBtn');
        if (showBtn) showBtn.style.display = 'inline-block';
      }
    });

  const showBtn = document.getElementById('showCommentAreaBtn');
  const commentArea = document.getElementById('commentArea');
  const addCommentBtn = document.getElementById('addComment');
  const commentContent = document.getElementById('commentContent');
  const commentList = document.getElementById('commentList');

  if (showBtn) {
    // 버튼 누르면 댓글 작성 영역 보이기
    showBtn.addEventListener('click', () => {
      if (commentArea) commentArea.style.display = 'block';
    });
  }

  if (addCommentBtn) {
    // 댓글 등록 버튼 클릭 시 간단 저장 로직 (예: 리스트에 임시 추가)
    addCommentBtn.addEventListener('click', () => {
      const content = commentContent.value.trim();
      if (!content) {
        alert('댓글 내용을 입력해주세요.');
        return;
      }

      // 실제로는 서버에 댓글 저장 API 호출 필요
      // 예시 UI 임시 추가
      const newComment = document.createElement('li');
      newComment.textContent = content;  // 필요시 템플릿 맞게 수정
      if (commentList) commentList.appendChild(newComment);

      commentContent.value = ''; // 입력창 초기화
    });
  }
  addCommentBtn.addEventListener('click', () => {
  const content = commentContent.value.trim();
  if (!content) {
    alert('댓글 내용을 입력해주세요.');
    return;
  }

  // 댓글 데이터 (필요한 값은 상황에 맞게 조정)
  const commentData = {
    boardNo: targetBoardNo,       // 게시글 번호 (서버에서 동적 삽입 필요)
    memberNo: loginMemberNo,      // 로그인 회원 번호 (동적 삽입)
    commentContent: content
  };

  fetch('/comments', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(commentData)
  })
  .then(res => {
    if (!res.ok) throw new Error('댓글 등록 실패');
    return res.json();
  })
  .then(newComment => {
    // 댓글 등록 성공 시 댓글 리스트에 추가
    const li = document.createElement('li');
    // 간단 텍스트 표시. 실제로는 템플릿 맞춰서 넣어야 함
    li.textContent = newComment.commentContent + " - " + newComment.memberNickname;
    commentList.appendChild(li);

    commentContent.value = ''; // 입력창 초기화
  })
  .catch(err => {
    alert(err.message);
  });
});
