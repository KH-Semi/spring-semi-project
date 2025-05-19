document.addEventListener('DOMContentLoaded', () => {
  const input = document.getElementById('youtubeUrl');
  const container = document.getElementById('videoContainer');
  const confirmBtn = document.getElementById('confirmYouTubeBtn');
  const backBtn = document.getElementById('backToProfileBtn');
document.addEventListener('DOMContentLoaded', () => {
  const confirmEditBtn = document.getElementById('confirmEditBtn');

confirmEditBtn.addEventListener('click', () => {
  // 실제 값을 가져와서 data 객체에 넣기 (아래는 예시)
  const data = {
    memberNo: document.getElementById('memberNoInput').value,  // input 등에서 값 가져오기
    nickname: document.getElementById('nicknameInput').value,
    bio: document.getElementById('bioInput').value
  };

  console.log(data);  // 잘 들어왔는지 확인
  // 여기서 Ajax 요청 등으로 서버에 data를 전송하세요
});



    fetch('/saveProfile', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
    .then(response => {
      if (!response.ok) throw new Error('저장 실패');
      return response.json();
    })
    .then(result => {
      alert('저장 성공!');
      // 필요하면 저장 후 후처리 (페이지 리로드, 상태 갱신 등)
    })
    .catch(error => {
      alert('저장 중 오류 발생: ' + error.message);
    });
  });
});

  // 유튜브 URL 엔터 입력 이벤트
  input.addEventListener('keydown', function(e) {
    if (e.key === 'Enter') {
      const url = input.value.trim();

      let embedUrl = "";

      // 재생목록 확인
      const playlistMatch = url.match(/[?&]list=([a-zA-Z0-9_-]+)/);
      // 단일 영상 확인
      const videoMatch = url.match(/(?:youtube\.com\/.*[?&]v=|youtu\.be\/)([a-zA-Z0-9_-]{11})/);

      if (playlistMatch && url.includes("playlist")) {
        const listId = playlistMatch[1];
        embedUrl = `https://www.youtube.com/embed/videoseries?list=${listId}`;
      } else if (videoMatch) {
        const videoId = videoMatch[1];
        embedUrl = `https://www.youtube.com/embed/${videoId}`;
      } else {
        alert("올바른 유튜브 영상 또는 재생목록 URL을 입력해주세요.");
        return;
      }

      // iframe 삽입
      container.innerHTML = `
        <div class="embed-container">
          <iframe 
            src="${embedUrl}" 
            allowfullscreen 
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture">
          </iframe>
        </div>
      `;
    }
  });

  // Confirm 버튼 클릭 이벤트
  confirmBtn.addEventListener('click', () => {
    const iframe = container.querySelector('iframe');
    if (!iframe) {
      alert('먼저 유튜브 URL을 입력하고 영상을 불러와주세요.');
      return;
    }
    // 예: confirm 시 유튜브 영상 URL 또는 ID를 서버로 전송하거나 저장하는 로직 작성 가능
    alert('Confirm 버튼이 클릭되었습니다! 필요한 동작을 추가하세요.');
  });

  // Back 버튼 클릭 이벤트
  backBtn.addEventListener('click', () => {
    // 예: 프로필 페이지로 돌아가기
    // window.history.back(); // 이전 페이지로 이동
    // 또는 특정 URL로 이동
    // window.location.href = '/profile'; 
    alert('Back 버튼이 클릭되었습니다! 뒤로 가기 기능을 구현하세요.');
  });
