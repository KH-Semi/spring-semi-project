/**************AUTHOR: 안준성
 * html 문서와 마찬가지로 friendListPart.js를 복붙한 코드라 변수명이 대개 일치합니다..**************** */

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
/*friend를 왼쪽으로 밀어버리는 애니메이션 동작함수 + 해당 요소를 DOM에서 제거*/
const fadeOut = (friend) => {
  friend.classList.add("removing");

  // transition 완료 후 remove()
  friend.addEventListener(
    "transitionend",
    () => {
      friend.remove();
    },
    { once: true }
  );
};
// DB 내부 일촌테이블 DELETE 전용 애니메이션 함수(fadeOut과는 반대방향으로 밀어버림) +  안에서 해당 요소를 DOM에서 제거.
const deleteFade = (friend) => {
  friend.classList.add("deleting");

  // transition 완료 후 remove()
  friend.addEventListener(
    "transitionend",
    () => {
      friend.remove();
    },
    { once: true }
  );
};
/* changeTitleForSec()
  title : 동작을 수행할 웹페이지상 text 요소
  str : 바꿀 내용
*/
const changeTitleForSec = (title, str) => {
  if (title) {
    const savedText = title.innerText;
    title.innerText = str; // string
    setTimeout(() => {
      title.innerText = savedText;
    }, 1000);
  } else {
    console.log("title이 null 입니다!");
  }
};

/********************여기까지 주요 기능 이외 함수******************** */

//현재 로그인한 유저 정보(지금은 테스트중)
let memberN = /*[[${members}]]*/ [];

//buttons
const editBtn = document.getElementById("edit-button"); // edit
const applyCancelBtnDiv = document.getElementById("apply-cancel-button-div"); //apply-cancel btn 을 담는 div
const applyBtn = document.getElementById("apply-button");
const cancelBtn = document.getElementById("cancel-button");
const sendFriendReqBtn = document.getElementById("send-friend-request-button");
const friendSpans = document.querySelectorAll(".friend-item");
const friendSendedSpans = document.querySelectorAll(".friend-item-sended");
document.getElementById("back-btn").addEventListener("click", () => {
  history.back(); // 또는 history.go(-1);
});

friendSpans.forEach(function (friend) {
  console.log(friend);
});
function resetEditCancelBtn() {
  if (editBtn) {
    editBtn.addEventListener("click", (e) => {
      console.log("editBtn clicked!");
      applyCancelBtnDiv.classList.remove("hidden");
      applyBtn.classList.remove("hidden");
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
        friend
          .querySelector("[name=unfollow-button]")
          .classList.remove("hidden");
      });
    });
  }

  if (cancelBtn) {
    cancelBtn.addEventListener("click", (e) => {
      editBtn.classList.remove("hidden");
      applyCancelBtnDiv.classList.add("hidden");
      applyBtn.classList.add("hidden");
      e.target.classList.add("hidden");

      friendSpans.forEach(function (friend) {
        friend.querySelector("[name=fromNickname]").classList.remove("hidden");
        friend.querySelector("[name=ilchon-button]").classList.remove("hidden");
        friend
          .querySelector("[name=fromNickname-input]")
          .classList.add("hidden");
        friend.querySelector("[name=unfollow-button]").classList.add("hidden");
      });
    });
  }
}

//각 friendSpans의 <textarea>안 change이벤트 발생시 submit하는 이벤트 핸들러 지정

friendSpans?.forEach(function (friend) {
  //accept 버튼을 누르면 일촌명 지정을 위해 textarea의 hidden속성 제거
  if (friend) {
    const acceptButton = friend.querySelector("[name=accept-button]");
    const fromNicknameInput = friend.querySelector("[name=fromNickname-input]");
    acceptButton?.addEventListener("click", (e) => {
      console.log("accept button clicked!");
      // 1. fetch
      const newNickName = friend.querySelector(
        "[name=fromNickname-input]"
      ).value; // input 내부 text를 newNickname 변수에 저장
      console.log(fromNicknameInput.value); // DEBUG용이므로 지우셔도 됩니다
      fetch("/update/accept", {
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
          console.log(data); // DEBUG용이므로 지우셔도 됩니다
          if (data.status == 2) {
            console.log("toNickname 수정 성공!");

            fadeOut(friend);
            changeTitleForSec(
              document.getElementById("incoming-title"),
              "새 일촌을 수락했습니다!! 잘부탁드려요!"
            );
          } else if (data.status == 1) {
            console.log("fromNickname 수정 성공!");

            checkIcon(friend);

            fadeOut(friend);

            /* return fetch(`/${memberNo}/friendList/incoming`);*/
          } else {
            console.log("수정 실패!");
            xIcon(friend);
            return Promise.reject("닉네임 수정 실패");
          }
        })
        .catch((err) => {
          console.error(err);
        });
    });
    //accept 버튼 누른후 nickname 수정요청 + is_ilchon = 'Y'로 변경
    fromNicknameInput?.addEventListener("input", (e) => {
      if (e.target.value.trim() !== "") {
        friend
          .querySelector("[name=accept-button]")
          .classList.remove("disabled-button"); // accept 버튼 활성화
        friend
          .querySelector("[name=accept-button]")
          .classList.add("hover-green"); // accept 버튼 활성화
      } else {
        // input의 내용이 다시 비게 되었을때
        friend
          .querySelector("[name=accept-button]")
          .classList.add("disabled-button"); // accept 버튼 다시 비활성화
        friend
          .querySelector("[name=accept-button]")
          .classList.remove("hover-green"); // accept 버튼 다시 비활성화
      }
    });

    friend // deny 버튼 누른후 이벤트 핸들러 설정(accept 버튼 누른후 fetch문을 그대로 복사해서 필요한 부분만 바꾼 코드!!)
      .querySelector("[name=deny-button]")
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

              deleteFade(friend); // 일촌테이블 DELETE 전용 동작함수. 안에서 friend.remove()를 자체적으로 수행함.
            } else if (data.status == 0) {
              console.log("삭제 실패! 0");

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
  }
});
friendSendedSpans?.forEach(function (friend) {
  if (friend) {
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
              deleteFade(friend); // 일촌테이블 DELETE 전용 동작함수. 안에서 friend.remove()를 자체적으로 수행함.
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
  }
});

// 미니홈피 이동 기능: 프로필 이미지 or 이름 클릭 시
[...friendSpans, ...friendSendedSpans].forEach(function (friend) {
  const memberNo = friend.dataset.memberNo;

  const profileImg = friend.querySelector(".friend-profile");
  const memberName = friend.querySelector(".friend-name");

  profileImg.style.cursor = "pointer";
  if (profileImg) {
    profileImg.addEventListener("click", () => {
      location.href = `/${memberNo}/minihome`;
    });
  }

  memberName.style.cursor = "pointer";
  if (memberName) {
    memberName.addEventListener("click", () => {
      location.href = `/${memberNo}/minihome`;
    });
  }
});
