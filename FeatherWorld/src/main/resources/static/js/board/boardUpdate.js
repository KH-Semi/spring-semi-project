// 한 번 기존 이미지를 삭제하면 버튼 자체를 disabled로 막을 예정!
// -> 그래서 배열로 해도 무관
let deletedImageList = []; // 삭제할 기존 이미지 번호들

// const formData = new FormData();
// const title = document.querySelector('input[name="boardTitle"]').value;
// const content = document.querySelector('textarea[name="boardContent"]').value;

// 이미지 삭제 토글 버튼
document.addEventListener("DOMContentLoaded", () => {
  const backButton = document.querySelector('.back-button span');
  
  const deleteImageBtn = document.querySelectorAll('.delete-existing-btn');
  if(deleteImageBtn) {
    deleteImageBtn.forEach(btn => {
      btn.addEventListener('click', () => {
        const imageItem = btn.closest('.existing-image-item');
        const imageNo = btn.dataset.imageNo;
        const icon = btn.querySelector('i');
        
        if (imageItem.classList.contains('deleted')) {
          // 복원 처리
          imageItem.classList.remove('deleted');
          btn.classList.remove('restore');
          icon.className = 'fa-solid fa-trash';
          
          // 삭제 목록에서 제거
          deletedImageList = deletedImageList.filter(id => id !== imageNo);
        } else {
          // 삭제 처리
          imageItem.classList.add('deleted');
          btn.classList.add('restore');
          icon.className = 'fa-solid fa-undo';
          
          // 삭제 목록에 추가
          if (!deletedImageList.includes(imageNo)) {
            deletedImageList.push(imageNo);
          }
        }
        console.log(deletedImageList);
      })
    })
  }
})