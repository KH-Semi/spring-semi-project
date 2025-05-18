// 현재 게시판 종류 번호를 저장하는 boardCode 변수 선언
let boardCode = currentBoardCode;

// 쓰기 버튼
const writeBtn = document.querySelector(".write-button");

/** 현재 페이지(cp)를 원하는 값으로 수정, url에 반영
 * @author Jiho
 * @param page
 */
const updateCp = (page) => {
  const url = new URL(window.location);
  url.searchParams.set('cp', page);
  history.replaceState({}, '', url);
}

/** 게시글 목록을 불러올 때, 게시글이 존재한다면 페이징 목록 생성
 * @author Jiho
 * @param pagination Pagination 객체
 */
const renderPagination = (pagination) => {
  // 기존 페이징 목록 div 가져오기
  const paginationContainer = document.querySelector(".pagination");
  // 임시 페이징 목록 div
  const tempPagination = document.createElement("div");
  tempPagination.classList.add("pagination");

  /** 각각의 페이징 목록을 생성하고, 페이지 변경 click 이벤트 부여
   * @author Jiho
   * @param {number} page 페이지 번호(cp)
   * @param {String} text innerText 내용
   * @param {string} className 클래스명
   * @returns {HTMLSpanElement} span 태그
   */
  const createPageSpan = (page, text, className = "") => {
    const span = document.createElement("span");
    span.innerText = text;
    span.dataset.page = String(page);
    if (className) span.classList.add(className);

    // 페이지 클릭 이벤트 추가
    span.addEventListener("click", async () => {
      await loadBoardList(boardCode, page);
    });

    return span;
  };

  // << 첫 페이지
  tempPagination.append(createPageSpan(1, "<<"));

  // < 이전 페이지
  tempPagination.append(createPageSpan(pagination.prevPage, "<", "page-nav"));

  // 페이지 번호 목록
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    const span = createPageSpan(i, i);
    if (i === pagination.currentPage) {
      span.classList.add("current");
    }
    tempPagination.append(span);
  }

  // > 다음 페이지
  tempPagination.append(createPageSpan(pagination.nextPage, ">", "page-nav"));

  // >> 마지막 페이지
  tempPagination.append(createPageSpan(pagination.maxPage, ">>"));

  // 기존 페이징 목록이 있다면 변경
  if(paginationContainer) paginationContainer.replaceWith(tempPagination);
  // 기존 페이징 목록이 없으면서 쓰기 버튼이 있다면 추가
  else if(writeBtn) writeBtn.before(tempPagination);
};

/** 게시판 종류별 게시글 목록 불러오는 메서드
 * @author Jiho
 * @param {number} boardCode 게시판 종류 번호
 * @param {number} page 페이지 번호(cp)
 * @returns {HTMLDivElement} 비동기로 가져온 게시글 목록 div
 */
const loadBoardList = async (boardCode, page) => {
  /** 특정 class를 가진 div 태그 생성
   * @author Jiho
   * @param {string} className 클래스명
   * @param {string} text innerText 내용
   * @returns {HTMLDivElement} div 태그
   */
  const createDiv = (className = "", text = "") => {
    const div = document.createElement("div");
    if (className) div.classList.add(className);
    if (text) div.innerText = text;

    return div;
  };

  // 게시글 목록 div 생성
  const boardContainer = createDiv("board-list");

  // ajax를 통해 비동기로 회원별 게시판 목록 각 요소 클릭시 boardList & pagination 구해옴
  const resp = await fetch(`/board/type/${boardCode}?cp=${page}`);
  const map = await resp.json();

  // map 내부에 있는 pagination & boardList 선언
  const pagination = map.pagination;
  const boardList = map.boardList;

  if(pagination) { // 요청한 map에 페이징 목록이 있는 경우
    renderPagination(pagination)

  } else { // 요청한 map에 페이징 목록이 없는 경우
    const paginationContainer = document.querySelector(".pagination");
    /// 기존 페이징 목록이 있다면 제거
    if(paginationContainer) paginationContainer.remove();
  }

  // 게시글이 없는 경우
  if(boardList == null) {
    const span = document.createElement("span");
    span.innerText = "게시글이 존재하지 않습니다.";

    boardContainer.append(span);
    // 게시글 목록 div를 임시 게시글 목록 div로 교체
    boardContainer.replaceWith(boardContainer);

    return boardContainer;
  }

  // 비동기로 가져온 게시글 내용을 꺼내서 html 요소로 대입
  for(const board of boardList) {

    // 게시글 하나를 담는 div 생성
    const boardItem = createDiv("board-item");

    // 게시글 썸네일과 게시글 제목/내용을 담는 묶음 div 생성
    const boardWrap = createDiv("board-wrap");

    // 게시글 썸네일이 있을 경우에만 썸네일 이미지가 들어간 div 넣어줌
    if(board.thumbnail) {
      const boardThumbnail = createDiv("board-thumbnail");

      const thumbnailImg = document.createElement("img");
      thumbnailImg.src = board.thumbnail;
      thumbnailImg.alt = "thumbnail";

      boardThumbnail.append(thumbnailImg);
      boardWrap.append(boardThumbnail);
    }

    // 게시글 제목/내용을 담은 div 생성
    const boardMain = createDiv("board-main");

    boardMain.append(createDiv("board-title", board.boardTitle));
    boardMain.append(createDiv("board-content", board.boardContent));

    boardWrap.append(boardMain);

    // 게시글 작성일/조회수를 담는 묶음 div 생성
    const boardInfo = createDiv("board-info");

    // 게시글 작성일 담은 div 생성, 추가
    boardInfo.append(createDiv("board-date", board.boardWriteDate));

    // 게시글 조회수를 담은 div 생성
    const boardReads = createDiv("board-reads");

    const iconRead = document.createElement("i");
    iconRead.classList.add("icon-read");
    const readCount = document.createElement("span");
    readCount.innerText = board.readCount;
    
    boardReads.append(iconRead, readCount);
    boardInfo.append(boardReads);

    // 게시글 하나에 모든 내용 넣기
    boardItem.append(boardWrap, boardInfo);

    // 게시글 목록 div에 게시글 하나 넣기
    boardContainer.append(boardItem);
  }

  return boardContainer;
}

// 좌측 게시판 목록 선택
// 게시판 목록 div 태그들을 차례대로 선택, 안에 있는 th:data-board-code 로부터 boardCode를 얻어와서 click 이벤트 추가
document.querySelectorAll(".board-type-item").forEach(boardTypeItem => {

  boardTypeItem.addEventListener("click", async () => {

    // url 속 쿼리 스트링을 통해 현재 페이지 값(cp) 1로 수정
    // 게시글이 없는 경우에도 cp 값은 1, 반환 map 내용만 다름
    updateCp(1);

    boardCode = boardTypeItem.dataset.boardCode;

    // 기존 게시글 목록 div
    const boardContainer = document.querySelector(".board-list");
    // 새로운 게시글 목록 div
    const newBoardContainer = await loadBoardList(boardCode, 1);

    // 게시글 목록 div 교체
    boardContainer.replaceWith(newBoardContainer);
  });
});

if(writeBtn) {
  writeBtn.firstElementChild.addEventListener("click", () => {

    if(boardCode === 0) {
      alert("존재하지 않는 게시판입니다.");
      return;
    }

    // 현재 게시판에 게시글을 쓸 수 있도록 이동
    location.href=`/board/type/${boardCode}/write`;
    
    // 비동기로 수정해야 됨.......

  });
}