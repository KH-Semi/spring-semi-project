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
      //unfollow 요청 서버로 보내기
      friend.querySelector("[name=unfollow-button]").addEventListener(() => {
        // unfollow 비동기 요청
      });
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

if (applyBtn) {
  applyBtn.addEventListener("click", () => {
    // 1. fetch (or submit?)
    // 2. 각 요소들이 삽입되면 다시 th:block만 refresh 하거나 현재 페이지로 redirect(pagination 유지할것)
  });
}
