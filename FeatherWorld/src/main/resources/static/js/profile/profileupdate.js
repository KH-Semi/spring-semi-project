const profileForm = document.getElementById("profileForm");

if (profileForm != null) {

  const imageInput = document.getElementById("uploadFile");
  const previewImg = document.getElementById("preview");
  const bioInput = document.getElementById("bio-input"); // 변경된 id
  const MAX_SIZE = 1024 * 1024 * 5; // 5MB

  let previousImage = previewImg.src;
  let previousFile = null;

  if (imageInput && previewImg) {
    imageInput.addEventListener("change", () => {
      const file = imageInput.files[0];
      if (file) {
        if (file.size <= MAX_SIZE) {

          // 현재 선택한 파일의 크기가 허용범위 이내인 경우(정상인 경우)
          const newImageUrl = URL.createObjectURL(file); // 임시 URL 생성
          // URL.createObjectURL(file) : 웹에서 접근 가능한 임시 URL 반환
          //console.log(newImageUrl);
          previewImg.src = newImageUrl; // 미리보기 이미지 설정
          // (img 태그의 src에 선택한 파일 임시 경로 대입)
          previousImage = newImageUrl; // 현재 선택된 이미지를 이전 이미지로 저장(다음에 바뀔일에 대비)
          previousFile = file; // 현재 선택된 파일 객체를 이전 파일로 저장 (다음에 바뀔일에 대비)
          statusCheckImg = 1; // 새 이미지 선택 상태 기록
        } else {
          // 파일 크기가 허용번위를 초과한 경우
          alert("5MB 이하의 이미지를 선택해주세요!");
          imageInput.value = ""; // 1. 파일 선택 초기화
          previewImg.src = previousImage; // 2. 이전 미리보기 이미지로 복원

           // 기존 Blob URL 해제
      if (previousImage && previousImage.startsWith("blob:")) {
        URL.revokeObjectURL(previousImage);
      }
          const newImageUrl = URL.createObjectURL(file);
          previewImg.src = newImageUrl;
          previousImage = newImageUrl;
          previousFile = file;
        } else {
          alert("5MB 이하의 이미지를 선택해주세요!");
          imageInput.value = "";
          previewImg.src = previousImage;

          if (previousFile) {
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(previousFile);
            imageInput.files = dataTransfer.files;
          }
        }
      } else {
        previewImg.src = previousImage;
        if (previousFile) {
          const dataTransfer = new DataTransfer();
          dataTransfer.items.add(previousFile);
          imageInput.files = dataTransfer.files;
        }
      }
    });

  profileForm.addEventListener("submit", (e) => {
    if (!bioInput) {
      console.error("bio-input 요소를 찾을 수 없습니다.");
      return;
    }

  });
}
}