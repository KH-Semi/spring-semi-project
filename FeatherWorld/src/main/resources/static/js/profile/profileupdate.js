const profileForm = document.getElementById("profileForm"); // 프로필 form

if (profileForm != null) {
  const imageInput = document.getElementById("uploadFile"); // input 태그
  const previewImg = document.getElementById("preview"); // img태그
  const previewBio = document.getElementById("bio-input"); // img태그
  let statusCheckImg = 0; // 0 : 초기 상태,  1 : 새 이미지 선택
  // img 태그에 작성하는 값 src = 미리보기 이미지를 띄울 URL 주소

  let previousImage = previewImg.src;

  // 이전 이미지 URL 기록(초기 상태의 이미지 URL 저장)
  // input (type-file) 태그가 작성할 값 = 서버에 실제로 제출되는 File 객체
  let previousFile = null; // 이전에 선택된 파일 객체를 저장
  const MAX_SIZE = 1024 * 1024 * 5; // 최대 파일 크기 설정 (5MB)

  // 1. 이미지 미리보기
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

          // 3. 파일 입력 복구 : 이전 파일이 존재하면 다시 할당
          if (previousFile) {
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(previousFile);
            imageInput.files = dataTransfer.files;
          }
        }
      } else {
        // 파일이 선택되지 않은 경우( == 취소를 누른 경우 )
        // 이전 미리보기 이미지로 복원 (img)
        previewImg.src = previousImage;

        // 이전 선택한 파일로 복원 (input)
        if (previousFile) {
          // 이전 파일이 존재한다면
          const dataTransfer = new DataTransfer();
          dataTransfer.items.add(previousFile);
          imageInput.files = dataTransfer.files;
        }
      }
    });

    // 폼 제출 시 유효성 검사
    profileForm.addEventListener("submit", (e) => {
      if (statusCheckImg == 0) {
        // 변경 사항이 없는 경우 제출 막기
        e.preventDefault();
        alert("이미지 변경 후 제출하세요!");
      } else if (previewBio.length === 0) {
        // 변경 사항이 없는 경우 제출 막기
        e.preventDefault();
        alert("이미지 변경 후 제출하세요!");
      }
    });
  }
}
