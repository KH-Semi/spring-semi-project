// 일촌 신청 함수
function follow(toMemberNo) {
  if (!confirm("이 분에게 일촌 신청을 보내시겠습니까?")) {
    return;
  }

  const followBtn = document.getElementById("profileFollowBtn");
  if (followBtn) {
    followBtn.disabled = true;
    followBtn.textContent = "신청 중...";
  }

  fetch(`/${toMemberNo}/follow`, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      //application/x-www-form-urlencoded    / toMemberNo=8   / HTML폼, 간단한 데이터
      // json은 js객체형이라 맵같은 key:value 값이 와야되는데 이건 좀 간단하게 쓰깁편함
      //  HTML 데이터 RequestParam으로 단일데이터만 필요할때는 이런식으로 요청해서 받을수잇음
      // 근데 .. json이 더좋은것같기도 ?!
    },
    body: "toMemberNo=" + toMemberNo,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("네트워크 오류: " + response.status);
      }
      return response.json();
    })
    .then((data) => {
      if (data.success) {
        alert("✅" + data.message); //유니코드 이모지 ㅋㅋ
        if (followBtn) {
          followBtn.textContent = "신청됨";
          followBtn.style.backgroundColor = "#6c757d";
          followBtn.disabled = true;
        }
      } else {
        alert("❌ " + data.message); //유니코드 이모지 ㅋㅋ
        if (followBtn) {
          followBtn.disabled = false;
          followBtn.textContent = "Follow";
        }
      }
    });
}

// 미수락 팔로워 신청 보기
function showPendingFollowers() {
  //아직 미구현 ..
  alert(
    "새로운 일촌 신청이 있습니다!\n일촌 신청 목록 페이지로 이동하시겠습니까?"
  );
  // window.location.href = '/friend/requests'; // 일촌 신청 목록 페이지로 이동
}

// 프로필 이미지 수정
function editProfileImage() {
  //아직 미구현 ..
  alert("프로필 이미지 수정 기능입니다.\n(구현 예정)");
}

// 프로필 수정
function editProfile() {
  //아직 미구현 ..
  alert("프로필 수정 기능입니다.\n(구현 예정)");
}

// 로그아웃
function logout() {
  if (confirm("로그아웃 하시겠습니까?")) {
    fetch("/member/logout", {
      method: "POST",
      credentials: "same-origin",
      //  credentials: "same-origin",  쿠키/세션 정보 함께 전송  >> 서버에서 로그인된 사용자 확인 가능
      // 그래서 이걸씀 .. 개꿀이네
      // 사실 우리가 쿠키에 저장되는 카카오 로그인값들은 어차피 처리안됌 ..
    })
      .then(() => {
        window.location.href = "/";
      })
      .catch((error) => {
        console.error("로그아웃 오류:", error);
        // 에러가 발생해도 메인 페이지로 이동
        window.location.href = "/";
      });
  }
}

// 서핑 (랜덤 미니홈 방문)
function goSurfing() {
  // 구현 예정 ..
  if (confirm("랜덤으로 다른 사람의 미니홈을 방문하시겠습니까?")) {
    //   // 서버에서 랜덤 회원 번호를 받아와서 이동
    //   // 일촌중인 랜덤한 회원의 번호
    //     fetch('/surfing')
    //     .then(response => response.json())
    //     .then(data => {
    //         if (data.memberNo) {
    //             window.location.href = '/' + data.memberNo + '/minihome';
    //         } else {
    //             alert('현재 방문할 수 있는 미니홈이 없습니다.');
    //         }
    //     })
    //     .catch(error => {
    //         console.error('서핑 오류:', error);
    //         alert('서핑 중 오류가 발생했습니다.');
    //     });
  }
}

// 프로필 수정 모드 관련 함수들
function confirmEdit() {
  // 구현 예정 ..
  alert("프로필 수정을 저장합니다.\n(구현 예정)");
}

function cancelEdit() {
  // 구현 예정 ..
  alert("프로필 수정을 취소합니다.\n(구현 예정)");
}

// DOM 로드 완료 시 초기화
document.addEventListener("DOMContentLoaded", function () {
  console.log("MiniHome JavaScript 로드 완료");

  // 필요한 초기화 작업들
  // 예: 알림 확인, 이벤트 리스너 추가 등
});
