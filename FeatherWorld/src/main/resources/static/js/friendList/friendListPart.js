//현재 로그인한 유저 정보(지금은 테스트중)
let memberN = /*[[${members}]]*/ [];
// 미수락 팔로워 신청 보기
function showPendingFollowers() {
  window.location.href = `/${memberNo}/friendList/incoming`; // 일촌 신청 목록 페이지로 이동
}
const sendFriendRequestButton = document.getElementById(
  "send-friend-request-button"
);

if (sendFriendRequestButton) {
  /*switch (sendFriendRequestButton.dataset.ilchonStatus) {
    case 0:
      sendFriendRequestButton.classList.remove("hidden");
      sendFriendRequestButton.innerText = "일촌 요청 보내기기";
      break;
    case 1:
      sendFriendRequestButton.classList.remove("hidden");
      sendFriendRequestButton.innerText = "일촌 요청 이미 보냄!";
      break;
    default:
      sendFriendRequestButton.classList.add("hidden");
      document.querySelector("#ilchon-flag").classList.remove("hidden");
      break;
  }*/
  sendFriendRequestButton.addEventListener("click", () => {
    window.location.href = `/${memberNo}/newFriend/input`;
  });
}
//buttons
const editBtn = document.getElementById("edit-button"); // edit
const applyCancelBtnDiv = document.getElementById("apply-cancel-button-div"); //apply-cancel btn 을 담는 div
/*const applyBtn = document.getElementById("apply-button");*/
const cancelBtn = document.getElementById("cancel-button");
const sendFriendReqBtn = document.getElementById("send-friend-request-button");
const friendSpans = document.querySelectorAll(".friend-item");
/*
sendFriendReqBtn.addEventListener("click", () => {
  fetch("/insert/new", {
    method: "POST", // ← POST로 바꿔야 body 사용 가능
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      memberNo: "00",
      nickname: newNickName,
    }), // TO_NICKNAME/FROM_NICKNAME 판별 여부는 서버측에서 판단
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      if (data.status == 2) {
        console.log("toNickname 수정 성공!");
        e.target.value = data.Ilchon.toNickname;
        if (friend) {
          friend.querySelector("[name=fromNickname]").textContent =
            data.Ilchon.toNickname;
        } //refresh
        checkIcon(friend);
      } else if (data.status == 1) {
        console.log("fromNickname 수정 성공!");
        e.target.value = data.Ilchon.fromNickname;
        if (friend) {
          friend.querySelector("[name=fromNickname]").textContent =
            data.Ilchon.fromNickname;
        } //refresh
        checkIcon(friend);
      } else {
        console.log("수정 실패!");
        xIcon(friend);
      }
    });
});*/
if (friendSpans) {
  friendSpans.forEach(function (friend) {
    console.log(friend);
  });
}

if (editBtn) {
  editBtn.addEventListener("click", (e) => {
    console.log("editBtn clicked!");
    applyCancelBtnDiv.classList.remove("hidden");
    /*applyBtn.classList.remove("hidden");*/
    cancelBtn.classList.remove("hidden");
    e.target.classList.add("hidden");

    friendSpans.forEach(function (friend) {
      friend.querySelector("[name=fromNickname]").classList.add("hidden");
      friend.querySelector("[name=ilchon-button]").classList.add("hidden");
      friend
        .querySelector("[name=fromNickname-input]")
        .classList.remove("hidden");
      friend.querySelector("[name=fromNickname-input]").innerText =
        friend.querySelector("[name=fromNickname]").innerText;
      friend.querySelector("[name=unfollow-button]").classList.remove("hidden");
      /* 첫 요소밖에 뜨지않는 이슈로 잠시 주석처리해둠.
      //unfollow 요청 서버로 보내기
      friend.querySelector("[name=unfollow-button]").addEventListener(() => {
        // unfollow 비동기 요청
      });*/
    });
  });
}

if (cancelBtn) {
  cancelBtn.addEventListener("click", (e) => {
    editBtn.classList.remove("hidden");
    applyCancelBtnDiv.classList.add("hidden");
    /*applyBtn.classList.add("hidden");*/
    e.target.classList.add("hidden");

    friendSpans.forEach(function (friend) {
      friend.querySelector("[name=fromNickname]").classList.remove("hidden");
      friend.querySelector("[name=ilchon-button]").classList.remove("hidden");
      friend.querySelector("[name=fromNickname-input]").classList.add("hidden");
      friend.querySelector("[name=unfollow-button]").classList.add("hidden");
    });
  });
}
/*
if (applyBtn) {
  applyBtn.addEventListener("click", () => {
    // 1. fetch (or submit?)

    // 2. 각 요소들이 삽입되면 다시 th:block만 refresh 하거나 현재 페이지로 redirect(pagination 유지할것)
  });
}*/

//각 friendSpans의 <textarea>안 change이벤트 발생시 submit하는 이벤트 핸들러 지정
friendSpans.forEach(function (friend) {
  friend
    .querySelector("[name=fromNickname-input]")
    .addEventListener("change", (e) => {
      // 1. fetch
      const newNickName = e.target.value;
      console.log(e.target.value);
      fetch("/update/nickname", {
        method: "POST", // ← POST로 바꿔야 body 사용 가능
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          memberNo: parseInt(friend.dataset.memberNo),
          nickname: newNickName,
        }), // TO_NICKNAME/FROM_NICKNAME 판별 여부는 서버측에서 판단
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          if (data.status == 2) {
            console.log("toNickname 수정 성공!");
            e.target.value = data.Ilchon.toNickname;
            if (friend) {
              friend.querySelector("[name=fromNickname]").textContent =
                data.Ilchon.toNickname;
            } //refresh
            checkIcon(friend);
          } else if (data.status == 1) {
            console.log("fromNickname 수정 성공!");
            e.target.value = data.Ilchon.fromNickname;
            if (friend) {
              friend.querySelector("[name=fromNickname]").textContent =
                data.Ilchon.fromNickname;
            } //refresh
            checkIcon(friend);
          } else {
            console.log("수정 실패!");
            xIcon(friend);
          }
        });
    });

  friend // unfollow 버튼 누른후 이벤트 핸들러 설정(accept 버튼 누른후 fetch문을 그대로 복사해서 필요한 부분만 바꾼 코드!!)
    .querySelector("[name=unfollow-button]")
    .addEventListener("click", (e) => {
      // 1. fetch
      const newNickName = e.target.value;
      console.log(e.target.value); // DEBUG용이므로 지우셔도 됩니다
      fetch("/delete", {
        method: "POST", // ← POST로 바꿔야 body 사용 가능

        headers: {
          "Content-Type": "application/json",
        },

        body: JSON.stringify({
          memberNo: parseInt(friend.dataset.memberNo),
        }),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data); // DEBUG용이므로 지우셔도 됩니다
          if (data.status == 1) {
            console.log("삭제 성공!");
            /*e.target.value = data.Ilchon.toNickname;
            if (friend) {
              friend.querySelector("[name=fromNickname]").textContent =
                data.Ilchon.toNickname;
            } //refresh*/

            checkIcon(friend);
            friend.remove();
          } else if (data.status == 0) {
            console.log("삭제 실패! 0");
            /* e.target.value = data.Ilchon.fromNickname;
            if (friend) {
              friend.querySelector("[name=fromNickname]").textContent =
                data.Ilchon.fromNickname;
            } //refresh*/
            checkIcon(friend);
          } else {
            console.log("삭제 실패! -1");
            xIcon(friend);
          }
        })
        .catch((err) => {
          console.error(err);
        });
    });
});
/**일촌명 수정 성공시 뜨는 초록색 V아이콘 */
const checkIcon = (friend) => {
  // 매개변수로 현재 일촌<span> 전달
  const icon = friend.querySelector(".check-icon");
  icon.classList.remove("hidden");

  setTimeout(() => {
    icon.classList.add("hidden");
  }, 1500);
};
/**일촌명 수정 실패시 뜨는 빨간색 V아이콘 */
const xIcon = (friend) => {
  // 매개변수로 현재 일촌<span> 전달
  const icon = friend.querySelector(".x-icon");
  icon.classList.remove("hidden");

  setTimeout(() => {
    icon.classList.add("hidden");
  }, 1500);
};
