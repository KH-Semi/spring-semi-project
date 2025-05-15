document.addEventListener('DOMContentLoaded', function () {
   // DOM 요소 가져오기
  const boardListFragment = document.getElementById('boardListFragment');
  const editBoardListBtn = document.getElementById('editBoardListBtn');
  const folderList = document.getElementById('folderList');
  const addFolderBtn = document.getElementById('addFolderBtn');
  const newFolderForm = document.getElementById('newFolderForm');
  const newFolderInput = document.getElementById('newFolderInput');
  const makeNewBtn = document.getElementById('makeNewBtn');
  const cancelBtn = document.getElementById('cancelBtn');

  // 전역 상태 설정
  let isEditMode = false;
  let nextFolderId = 2; // 새 폴더 ID의 시작값

  // 수정 모드 토글 버튼 이벤트
  editBoardListBtn.addEventListener('click', function () {
    isEditMode = !isEditMode;

    // 모든 폴더의 수정/삭제 아이콘 보이거나 숨기기
    document.querySelectorAll('.folder-actions').forEach(actions => {
      if (isEditMode) {
        actions.classList.add('show');
      } else {
        actions.classList.remove('show');
      }
    });
  });

  // 폴더 클릭 시 동작
  folderList.addEventListener('click', function (e) {
    const folderItem = e.target.closest('.folder-item');

     // 수정 모드 중이거나 수정 버튼을 클릭했을 경우 무시
    if (e.target.closest('.folder-actions') || e.target.classList.contains('folder-input')) {
      return;
    }

    if (folderItem) {
      // 모든 폴더의 active 클래스 제거
      document.querySelectorAll('.folder-item').forEach(item => {
        item.classList.remove('active');
      });

      // 클릭한 폴더에 active 클래스 추가
      folderItem.classList.add('active');
    }
  });

  // 폴더 이름 수정 버튼 클릭 시
  folderList.addEventListener('click', function (e) {
    const editBtn = e.target.closest('.edit-folder-btn');

    if (editBtn) {
      const folderItem = editBtn.closest('.folder-item');
      const folderContent = folderItem.querySelector('.folder-content');
      const folderName = folderItem.querySelector('.folder-name');
      const currentName = folderName.textContent;

      // 폴더 이름 대신 입력창으로 교체
      const input = document.createElement('input');
      input.type = 'text';
      input.className = 'folder-input';
      input.value = currentName;
      folderName.style.display = 'none';
      folderContent.appendChild(input);
      input.focus();

      // 입력창에서 포커스 잃거나 Enter 누르면 수정 완료
      input.addEventListener('blur', finishEditing);
      input.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
          finishEditing();
        }
      });

      function finishEditing() {
        const newName = input.value.trim();
        if (newName) {
          folderName.textContent = newName;
        }
        folderName.style.display = '';
        input.remove();
      }
    }
  });

  // 폴더 삭제 버튼 클릭 시
  folderList.addEventListener('click', function (e) {
    const deleteBtn = e.target.closest('.delete-folder-btn');

    if (deleteBtn) {
      const folderItem = deleteBtn.closest('.folder-item');

      // 기본 폴더는 삭제하지 않음
      if (folderItem.dataset.default === 'true') {
        return;
      }

      // 폴더 삭제
      folderItem.remove();
    }
  });

  // 폴더 추가 버튼 클릭 시 폼 보이기
  addFolderBtn.addEventListener('click', function () {
    newFolderForm.classList.add('show');
    newFolderInput.focus();
  });

  // 폴더 생성 버튼 클릭 시
  makeNewBtn.addEventListener('click', createNewFolder);

  // Enter 키 입력 시 폴더 생성
  newFolderInput.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
      createNewFolder();
    }
  });

  // 취소 버튼 클릭 시 폴더 입력 폼 숨기기
  cancelBtn.addEventListener('click', function () {
    newFolderForm.classList.remove('show');
    newFolderInput.value = '';
  });

  // 폴더 생성 함수
  function createNewFolder() {
    const folderName = newFolderInput.value.trim();
    const privacy = document.querySelector('input[name="privacy"]:checked').value;

    if (!folderName) {
      alert('Please enter a folder name');
      return;
    }

    // 새 폴더 아이템 생성
    const newFolder = document.createElement('li');
    newFolder.className = 'folder-item';
    newFolder.dataset.id = nextFolderId++;

    // 친구공개일 경우 아이콘 표시
    let friendIconHtml = '';
    if (privacy === 'friend') {
      friendIconHtml = `
        <span class="friend-icon">
        FRIEND
        </span>
    `;
    }

    newFolder.innerHTML = `
      <div class="folder-content">
          ${friendIconHtml}
          <span class="folder-name">${folderName}</span>
      </div>
      <div class="folder-actions ${isEditMode ? 'show' : ''}">
          <button class="edit-folder-btn">
            EDIT
          </button>
          <button class="delete-folder-btn">
            DELETE
          </button>
      </div>
                `;

    // 폴더 리스트에 추가
    folderList.appendChild(newFolder);

    // 폼 초기화
    newFolderForm.classList.remove('show');
    newFolderInput.value = '';

    // 테스트용 로그 출력
    console.log(`Folder "${folderName}" created with privacy: ${privacy}`);
  }

  // Board 탭 클릭 시 board 목록 보이기
  function showBoardList() {
    boardListFragment.style.display = 'block';
    console.log('Board list displayed');
  }

  // Board 탭 숨기기
  function hideBoardList() {
    boardListFragment.style.display = 'none';
    console.log('Board list hidden');
  }

  // 외부에서 접근할 수 있도록 함수 등록
  window.boardListModule = {
    show: showBoardList,
    hide: hideBoardList
  };

  // 페이지 로드시 폴더 로드 시뮬레이션
  function loadFoldersAsync() {
    
    console.log('Loading folders...');

    
    setTimeout(() => {
      console.log('Folders loaded successfully');
    }, 500);
  }

  // 페이지 로드시 자동 폴더 불러오기 실행
  loadFoldersAsync();
});

// -----------------------------------------------------------------

