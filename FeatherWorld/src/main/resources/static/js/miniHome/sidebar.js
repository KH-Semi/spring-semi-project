let member = /*[[$members]]*/ [];
/*
$(document).ready(function () {
  $.ajax({
    url: "/sidebar",
    method: "GET",
    success: function (data) {
      $("#left-sidebar-container").html(data);

      // 로그인 여부에 따라 숨기기 등 처리
      if ($("#left-sidebar-container").find(".Logout-button").length === 0) {
        $(".surfing-button").hide(); // 비로그인 시
        $("[follow-button]").hide(); // follow 버튼
      }
    },
    error: function () {
      $("#left-sidebar-container").html("<p>Sidebar loading failed.</p>");
    },
  });
});
*/
document.addEventListener("DOMContentLoaded", function () {
  const memberNo = document.body.getAttribute("data-member-no");
  const container = document.getElementById("sidebar-container");
  const path = window.location.pathname;

  const pathSegments = path.split("/");

  const memberNoString = pathSegments[1];

  const memberNoInt = parseInt(memberNoString);

  fetch(`/sidebar/${memberNoInt}`)
    .then((res) => {
      if (!res.ok) throw new Error("Failed to load sidebar");
      res.json();
    })
    .then((html) => {
      console.log("콘솔에 출력됩니다!");
      container.innerHTML = html;
    })
    .catch((err) => {
      console.error("Sidebar fetch error:", err);
    });
  if (!memberNo || !container) {
    console.warn("Sidebar fetch: memberNo 또는 container 없음");
    return;
  }
});
