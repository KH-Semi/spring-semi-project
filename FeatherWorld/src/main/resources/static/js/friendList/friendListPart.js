//현재 로그인한 유저 정보(지금은 테스트중)
let members = /*[[${members}]]*/ [];

//buttons
const editBtn = document.getElementById("edit-button"); // edit
const applyCancelBtnDiv = document.getElementById("apply-cancel-button-div"); //apply-cancel btn 을 담는 div
const applyBtn = document.getElementById("apply-button");
const cancelBtn = document.getElementById("cancel-button");

const friendSpans = document.querySelectorAll(".friend-item");
friendSpans.forEach(function (friend) {
  console.log(friend);
});

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
    applyBtn.classList.add("hidden");
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
});

const checkIcon = (friend) => {
  // 매개변수로 현재 일촌<span> 전달
  const icon = friend.querySelector(".check-icon");
  icon.classList.remove("hidden");

  setTimeout(() => {
    icon.classList.add("hidden");
  }, 1500);
};

const xIcon = (friend) => {
  // 매개변수로 현재 일촌<span> 전달
  const icon = friend.querySelector(".x-icon");
  icon.classList.remove("hidden");

  setTimeout(() => {
    icon.classList.add("hidden");
  }, 1500);
};
