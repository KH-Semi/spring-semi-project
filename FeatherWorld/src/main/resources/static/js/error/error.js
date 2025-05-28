// 검색 기능 구현
document.addEventListener('DOMContentLoaded', function() {
  const searchInput = document.getElementById('memberSearchInput');
  const searchButton = document.getElementById('searchButton');
  const searchResults = document.getElementById('searchResults');
  const closeBtn = document.getElementById('closeBtn');

  let searchTimeout;

  // 검색 입력 이벤트
  searchInput.addEventListener('input', function() {
    const query = this.value.trim();

    clearTimeout(searchTimeout);

    if (query.length < 2) {
      hideSearchResults();
      return;
    }

    // 디바운싱: 500ms 후에 검색 실행
    searchTimeout = setTimeout(() => {
      performSearch(query);
    }, 500);
  });

  // 검색 버튼 클릭
  searchButton.addEventListener('click', function() {
    const query = searchInput.value.trim();
    if (query.length >= 2) {
      performSearch(query);
    }
  });

  // 엔터키 검색
  searchInput.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
      const query = this.value.trim();
      if (query.length >= 2) {
        performSearch(query);
      }
    }
  });

  // 닫기 버튼
  closeBtn.addEventListener('click', function() {
    hideSearchResults();
    searchInput.value = '';
  });

  // 검색 실행 함수
  function performSearch(query) {
    // 실제 프로젝트에서는 아래 주석을 해제하고 더미 데이터 부분을 삭제하세요
    /*
    // 실제 API 호출 예시
    fetch(`/api/members/search?q=${encodeURIComponent(query)}`)
      .then(response => response.json())
      .then(results => {
        displaySearchResults(results);
      })
      .catch(error => {
        console.error('검색 오류:', error);
        displaySearchResults([]);
      });
    */

    // 더미 데이터 (실제 프로젝트에서는 삭제)
    const dummyResults = [
      { memberNo: 1, memberName: '김철수', memberImg: '/images/default/user.png' },
      { memberNo: 2, memberName: '이영희', memberImg: '/images/default/user.png' },
      { memberNo: 3, memberName: '박민수', memberImg: '/images/default/user.png' },
      { memberNo: 4, memberName: '최영수', memberImg: '/images/default/user.png' },
      { memberNo: 5, memberName: '정민아', memberImg: '/images/default/user.png' }
    ].filter(member => member.memberName.includes(query));

    displaySearchResults(dummyResults);
  }

  // 검색 결과 표시
  function displaySearchResults(results) {
    searchResults.innerHTML = '';

    if (results.length === 0) {
      const noResult = document.createElement('li');
      noResult.className = 'search-result-item';
      noResult.innerHTML = `
        <div style="text-align: center; color: #666; font-style: italic;">
          검색 결과가 없습니다.
        </div>
      `;
      searchResults.appendChild(noResult);
    } else {
      results.forEach(member => {
        const resultItem = document.createElement('li');
        resultItem.className = 'search-result-item';
        resultItem.innerHTML = `
          <div style="display: flex; align-items: center; gap: 10px;">
            <img src="${member.memberImg}" alt="${member.memberName}" 
                 style="width: 30px; height: 30px; border-radius: 50%; object-fit: cover; border: 1px solid #e0e0e0;">
            <span style="font-weight: 500;">${member.memberName}</span>
          </div>
        `;

        resultItem.addEventListener('click', function() {
          window.location.href = `/${member.memberNo}/minihome`;
        });

        searchResults.appendChild(resultItem);
      });
    }

    showSearchResults();
  }

  // 검색 결과 표시
  function showSearchResults() {
    searchResults.classList.add('active');
    closeBtn.style.display = 'block';
  }

  // 검색 결과 숨기기
  function hideSearchResults() {
    searchResults.classList.remove('active');
    closeBtn.style.display = 'none';
  }

  // 외부 클릭시 검색 결과 숨기기
  document.addEventListener('click', function(e) {
    if (!e.target.closest('.search-bar-404')) {
      hideSearchResults();
    }
  });

  // 검색창 포커스시 결과가 있으면 다시 표시
  searchInput.addEventListener('focus', function() {
    if (searchResults.children.length > 0) {
      showSearchResults();
    }
  });
});