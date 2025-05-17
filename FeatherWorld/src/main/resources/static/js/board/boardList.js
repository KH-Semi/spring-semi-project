// 현재 페이지(cp)를 원하는 값으로 수정, url에 반영하는 메서드
const updateQueryString = (pageNum) => {
  const url = new URL(window.location);
  url.searchParams.set('cp', pageNum);
  history.replaceState({}, '', url);
}

// 좌측 게시판 목록 선택
// 게시판 목록 div 태그들을 차례대로 선택, 안에 있는 th:data-board-code 로부터 boardCode를 얻어와서 click 이벤트 추가
document.querySelectorAll(".board-type-item").forEach(boardTypeItem => {

  boardTypeItem.addEventListener("click", async e => {

    // url 속 쿼리 스트링을 통해 현재 페이지 값(cp) 1로 수정
    // 게시글이 없는 경우에도 cp 값은 1, 반환 map 내용만 다름
    updateQueryString(1);

    // 기존 게시글 목록 요소 가져오기
    const boardListContainer = document.querySelector(".board-list");

    // 임시 게시글 목록 div 생성
    const tempBoardListContainer = document.createElement("div");
    tempBoardListContainer.classList.add("board-list");

    // ajax를 통해 비동기로 회원별 게시판 목록 각 요소 클릭시 boardList & pagination 구해옴
    const boardCode = e.target.dataset.boardCode;
    const resp = await fetch(`/board/type/${boardCode}`);
    const map = await resp.json();

    // map 내부에 있는 pagination & boardList 선언
    const pagination = map.pagination;
    const boardList = map.boardList;

    // 쓰기 버튼 게시판 종류 번호만 변경
    const writeBtn = document.querySelector(".write-button");
    writeBtn.dataset.boardCode = boardCode;

    // 요청한 map에 페이징 목록이 있는 경우
    if(pagination) {
      const tempPagination = document.createElement("div");
      tempPagination.classList.add("pagination");
      
      // 맨 처음 페이지, 이전 페이지
      const startPage = document.createElement("span");
      startPage.innerHTML = "&lt;&lt;"
      const prevPage = document.createElement("span");
      prevPage.classList.add("page-nav")
      prevPage.innerHTML = "&lt;";

      tempPagination.append(startPage, prevPage);

      // 각 페이지 번호
      for(let i = pagination.startPage; i <= pagination.endPage; i++) {
        const pageSpan = document.createElement("span");
        pageSpan.innerText = i;

        if(i === pagination.startPage) {
          pageSpan.classList.add("current");
        }

        tempPagination.append(pageSpan);
      }
      
      // 다음 페이지, 맨 마지막 페이지
      const nextPage = document.createElement("span");
      nextPage.classList.add("page-nav");
      nextPage.innerHTML = "&gt;";
      const endPage = document.createElement("span");
      endPage.innerHTML = "&gt;&gt;";

      // 임시 페이징 목록 div에 모두 넣기
      tempPagination.append(nextPage, endPage);

      const paginationContainer = document.querySelector(".pagination");
      // 기존 페이징 목록이 있다면 변경
      if(paginationContainer) paginationContainer.replaceWith(tempPagination);
      // 기존 페이징 목록이 없다면 추가
      else writeBtn.before(tempPagination);

    } else {
      // 요청한 map에 페이징 목록이 없는 경우

      const paginationContainer = document.querySelector(".pagination");
      /// 기존 페이징 목록이 있다면 제거
      if(paginationContainer) paginationContainer.remove();
    }

    // 게시글이 없는 경우
    if(boardList == null) {
      const span = document.createElement("span");
      span.innerText = "게시글이 존재하지 않습니다.";

      tempBoardListContainer.append(span);
      // 게시글 목록 div를 임시 게시글 목록 div로 교체
      boardListContainer.replaceWith(tempBoardListContainer);

      return;
    }

    // 비동기로 가져온 게시글 내용을 꺼내서 html 요소로 대입
    for(const board of boardList) {

      // 게시글 하나를 담는 div 생성
      const boardItem = document.createElement("div");
      boardItem.classList.add("board-item");

      // 게시글 썸네일과 게시글 제목/내용을 담는 묶음 div 생성
      const boardWrap = document.createElement("div");
      boardWrap.classList.add("board-wrap");

      // 게시글 썸네일이 있을 경우에만 썸네일 이미지가 들어간 div 넣어줌
      if(board.thumbnail) {
        const boardThumbnail = document.createElement("div");
        boardThumbnail.classList.add("board-thumbnail");

        const thumbnailImg = document.createElement("img");
        thumbnailImg.src = board.thumbnail;
        thumbnailImg.alt = "thumbnail";

        boardThumbnail.append(thumbnailImg);
        boardWrap.append(boardThumbnail);
      }

      // 게시글 제목/내용을 담은 div 생성
      const boardMain = document.createElement("div");
      boardMain.classList.add("board-main");

      const boardTitle = document.createElement("div");
      boardTitle.classList.add("board-title");
      boardTitle.innerText = board.boardTitle;

      const boardContent = document.createElement("div");
      boardContent.classList.add("board-content");
      boardContent.innerText = board.boardContent;

      boardMain.append(boardTitle, boardContent);
      boardWrap.append(boardMain);

      // 게시글 작성일/조회수를 담는 묶음 div 생성
      const boardInfo = document.createElement("div");
      boardInfo.classList.add("board-info");

      // 게시글 작성일/조회수를 담은 div 생성
      const boardDate = document.createElement("div");
      boardDate.classList.add("board-date");
      boardDate.innerText = board.boardWriteDate;

      const boardReads = document.createElement("div");
      boardReads.classList.add("board-reads");

      const iconRead = document.createElement("i");
      iconRead.classList.add("icon-read");
      const readCount = document.createElement("span");
      readCount.innerText = board.readCount;

      boardReads.append(iconRead, readCount);
      boardInfo.append(boardDate, boardReads);

      // 게시글 하나에 모든 내용 넣기
      boardItem.append(boardWrap, boardInfo);

      // 임시 게시글 목록 div에 게시글 하나 넣기
      tempBoardListContainer.append(boardItem);
    }
    // 게시글 목록 div를 임시 게시글 목록 div로 교체
    boardListContainer.replaceWith(tempBoardListContainer);

  });
});