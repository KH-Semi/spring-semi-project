// 이미지 업로드 관련 변수
let uploadedImages = []; // 업로드된 이미지들을 저장할 배열
const MAX_IMAGES = 5; // 최대 이미지 개수

// DOM 로드 완료 후 실행
document.addEventListener('DOMContentLoaded', function() {
  initializeImageUpload();
  initializeBackButton();
  initializeByteCounter();
});

// 이미지 업로드 초기화
function initializeImageUpload() {
  const imageSlots = document.querySelectorAll('.image-slot');
  const hiddenFileInput = document.getElementById('imageUpload');

  // 각 이미지 슬롯에 클릭 이벤트 추가
  imageSlots.forEach((slot, index) => {
    slot.addEventListener('click', function() {
      if (!slot.classList.contains('has-image')) {
        // 빈 슬롯 클릭 시 파일 선택 창 열기
        hiddenFileInput.dataset.slotIndex = index;
        hiddenFileInput.click();
      }
    });

    // 각 슬롯에 삭제 버튼 생성
    const removeBtn = document.createElement('button');
    removeBtn.className = 'remove-btn';
    removeBtn.innerHTML = '×';
    removeBtn.type = 'button';
    removeBtn.addEventListener('click', function(e) {
      e.stopPropagation();
      removeImage(index);
    });
    slot.appendChild(removeBtn);
  });

  // 파일 선택 시 이벤트 처리
  hiddenFileInput.addEventListener('change', function(e) {
    const file = e.target.files[0];
    const slotIndex = parseInt(this.dataset.slotIndex);

    if (file && file.type.startsWith('image/')) {
      addImageToSlot(file, slotIndex);
    }

    // input 초기화
    this.value = '';
    delete this.dataset.slotIndex;
  });
}

// 이미지를 슬롯에 추가
function addImageToSlot(file, slotIndex) {
  const slot = document.querySelectorAll('.image-slot')[slotIndex];
  const reader = new FileReader();

  reader.onload = function(e) {
    // 기존 placeholder 제거
    const placeholder = slot.querySelector('.placeholder');
    if (placeholder) {
      placeholder.remove();
    }

    // 이미지 엘리먼트 생성
    const img = document.createElement('img');
    img.src = e.target.result;
    img.alt = 'Uploaded image';

    // 슬롯에 이미지 추가
    slot.appendChild(img);
    slot.classList.add('has-image');

    // 업로드된 이미지 배열에 저장
    uploadedImages[slotIndex] = {
      file: file,
      dataUrl: e.target.result
    };

    console.log(`이미지가 슬롯 ${slotIndex + 1}에 추가되었습니다:`, file.name);
  };

  reader.readAsDataURL(file);
}

// 이미지 제거
function removeImage(slotIndex) {
  const slot = document.querySelectorAll('.image-slot')[slotIndex];

  // 이미지 엘리먼트 제거
  const img = slot.querySelector('img');
  if (img) {
    img.remove();
  }

  // has-image 클래스 제거
  slot.classList.remove('has-image');

  // placeholder 다시 추가
  if (!slot.querySelector('.placeholder')) {
    const placeholder = document.createElement('span');
    placeholder.className = 'placeholder';
    placeholder.innerHTML = '✕';
    slot.appendChild(placeholder);
  }

  // 업로드된 이미지 배열에서 제거
  delete uploadedImages[slotIndex];

  console.log(`슬롯 ${slotIndex + 1}의 이미지가 제거되었습니다.`);
}

// 뒤로가기 버튼 초기화
function initializeBackButton() {
  const backButton = document.querySelector('.back-button span');
  if (backButton) {
    backButton.addEventListener('click', function() {
      if (confirm('작성 중인 내용이 사라집니다. 정말 뒤로 가시겠습니까?')) {
        window.history.back();
      }
    });
  }
}

// 바이트 카운터 초기화
function initializeByteCounter() {
  const textarea = document.querySelector('.content-area textarea');
  const byteCounter = document.querySelector('.byte-counter');

  if (textarea && byteCounter) {
    // 초기값 설정
    updateByteCounter(textarea, byteCounter);

    // 입력 시마다 바이트 카운터 업데이트
    textarea.addEventListener('input', function() {
      updateByteCounter(textarea, byteCounter);
    });
  }
}

// 바이트 카운터 업데이트
function updateByteCounter(textarea, counter) {
  const text = textarea.value;
  const byteLength = new Blob([text]).size;
  const maxBytes = 2000;

  counter.textContent = `(${byteLength}/${maxBytes})bytes`;

  // 바이트 수가 초과되면 빨간색으로 표시
  if (byteLength > maxBytes) {
    counter.style.color = '#9f2120';
    textarea.style.borderColor = '#9f2120';
  } else {
    counter.style.color = '#777';
    textarea.style.borderColor = '#e0e0e0';
  }
}

// 폼 제출 시 이미지 데이터 처리
function handleFormSubmit() {
  const form = document.querySelector('.board-form');

  if (form) {
    form.addEventListener('submit', function(e) {
      e.preventDefault();

      // 폼 데이터 수집
      const formData = new FormData();
      const title = document.querySelector('input[placeholder="Board Title"]').value;
      const content = document.querySelector('textarea[placeholder="Board Content"]').value;

      // 기본 데이터 추가
      formData.append('title', title);
      formData.append('content', content);
      formData.append('boardCode', currentBoardCode);

      // 이미지 파일들 추가
      uploadedImages.forEach((imageData, index) => {
        if (imageData && imageData.file) {
          formData.append(`image_${index}`, imageData.file);
        }
      });

      // 실제 서버 전송 로직은 여기에 구현
      console.log('폼 제출 데이터:', {
        title: title,
        content: content,
        boardCode: currentBoardCode,
        imageCount: uploadedImages.filter(img => img).length
      });

      // 임시: 제출 성공 메시지
      alert('게시글이 성공적으로 작성되었습니다!');
    });
  }
}

// 취소 버튼 이벤트
document.addEventListener('DOMContentLoaded', function() {
  const cancelBtn = document.querySelector('.btn-cancel');
  if (cancelBtn) {
    cancelBtn.addEventListener('click', function() {
      if (confirm('작성 중인 내용이 모두 삭제됩니다. 정말 취소하시겠습니까?')) {
        // 폼 초기화
        document.querySelector('.board-form').reset();

        // 이미지 슬롯 초기화
        document.querySelectorAll('.image-slot').forEach((slot, index) => {
          if (slot.classList.contains('has-image')) {
            removeImage(index);
          }
        });

        // 업로드된 이미지 배열 초기화
        uploadedImages = [];

        // 바이트 카운터 초기화
        const byteCounter = document.querySelector('.byte-counter');
        if (byteCounter) {
          byteCounter.textContent = '(0/2000)bytes';
          byteCounter.style.color = '#777';
        }
      }
    });
  }
});

// 유틸리티 함수: 업로드된 이미지 정보 가져오기
function getUploadedImages() {
  return uploadedImages.filter(img => img);
}

// 유틸리티 함수: 특정 슬롯의 이미지 가져오기
function getImageFromSlot(slotIndex) {
  return uploadedImages[slotIndex] || null;
}