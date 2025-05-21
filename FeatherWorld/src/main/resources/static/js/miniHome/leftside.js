document.addEventListener("DOMContentLoaded", () => {
  fetch("/api/member/profile")
    .then((response) => {
      if (!response.ok) throw new Error("네트워크 에러");
      return response.json();
    })
    .then((data) => {
      if (!data) {
        console.log("로그인 필요");
        return;
      }
      // 프로필 이미지
      const profileImg = document.querySelector(".profile-image img");
      profileImg.src = data.memberImg ? data.memberImg : "/images/user.png";

      // 프로필 이름
      const profileName = document.querySelector(".profile-name");
      profileName.textContent = data.memberName || "회원";

      // 이메일
      const profileEmail = document.querySelector(".profile-handle");
      profileEmail.textContent = data.memberEmail || "";

      // follower / following 수 (만약 포함되어 있다면)
      const followerCount = document.querySelector(".stat-number:nth-child(1)");
      if (followerCount && data.followerCount !== undefined) {
        followerCount.textContent = data.followerCount;
      }
      const followingCount = document.querySelector(
        ".stat-number:nth-child(2)"
      );
      if (followingCount && data.followingCount !== undefined) {
        followingCount.textContent = data.followingCount;
      }

      // bio 등 필요한 부분도 동일하게 업데이트 가능
      const bioDisplay = document.getElementById("bio-display");
      if (bioDisplay && data.intro) {
        bioDisplay.textContent = data.intro;
      }
    })
    .catch((error) => console.error("프로필 로딩 실패:", error));
});
