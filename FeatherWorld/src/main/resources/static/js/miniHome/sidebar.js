$(document).ready(function () {
  $.ajax({
    url: '/sidebar',
    method: 'GET',
    success: function (data) {
      $('#left-sidebar-container').html(data);

      // 로그인 여부에 따라 숨기기 등 처리
      if ($('#left-sidebar-container').find('.Logout-button').length === 0) {
        $('.surfing-button').hide(); // 비로그인 시
        $('[follow-button]').hide(); // follow 버튼
      }
    },
    error: function () {
      $('#left-sidebar-container').html('<p>Sidebar loading failed.</p>');
    }
  });
});
