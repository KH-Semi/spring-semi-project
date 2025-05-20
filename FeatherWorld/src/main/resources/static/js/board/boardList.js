// 현재 게시판 종류 번호를 저장하는 boardCode 변수 선언
let boardCode = currentBoardCode;

/** 현재 선택한 페이지(cp)를 url에 반영 (history에 저장)
 * @author Jiho
 * @param {number} page
 */
const recodeCp = (page) => {
  const url = new URL(location);
  url.searchParams.set('cp', String(page));
  history.pushState({}, '', url);
}

/** 현재 url에 cp가 존재한다면 cp, 없다면 null 반환
 * @author Jiho
 */
const searchCp = () => {
  // URL 에서 cp 파라미터 추출
  const urlParams = new URLSearchParams(location.search);
  return parseInt(urlParams.get("cp")) || null;
}

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

/** 페이징 목록/글쓰기 버튼 포함 div 생성 메서드
 * @author Jiho
 * @param pagination Pagination 객체
 * @return 페이징 목록/글쓰기 버튼 포함 div
 */
const createBoardFooter = (pagination) => {

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
      // 변경된 cp값 적용/history 저장
      recodeCp(page);
      // 해당 페이지에 맞게 게시글/페이징 목록 갱신
      renderBoardList(boardCode, page)
          .catch(console.error);
    });

    return span;
  };

  const containerDiv = document.createElement("div");

  if(pagination) {
    // 임시 페이징 목록 div
    const updatedPagination = document.createElement("div");
    updatedPagination.classList.add("pagination");

    // << 첫 페이지
    updatedPagination.append(createPageSpan(1, "<<"));

    // < 이전 페이지
    updatedPagination.append(createPageSpan(pagination.prevPage, "<", "page-nav"));

    // 페이지 번호 목록
    for (let i = pagination.startPage; i <= pagination.endPage; i++) {
      const span = createPageSpan(i, i);
      if (i === pagination.currentPage) {
        span.classList.add("current");
      }
      updatedPagination.append(span);
    }

    // > 다음 페이지
    updatedPagination.append(createPageSpan(pagination.nextPage, ">", "page-nav"));

    // >> 마지막 페이지
    updatedPagination.append(createPageSpan(pagination.maxPage, ">>"));

    containerDiv.append(updatedPagination);
  }

  // 글쓰기 버튼 추가
  const writeDiv = createDiv("write-button");
  const writeSpan = document.createElement("span");
  writeSpan.innerText = "Write";
  writeDiv.append(writeSpan);

  // 쓰기 버튼 클릭시 동기식 페이지 전환
  writeSpan.addEventListener("click", () => {

    if(boardCode === 0) {
      alert("존재하지 않는 게시판입니다.");
      return;
    }

    location.href = `/${memberNo}/board/${boardCode}/write`;
  });

  containerDiv.append(writeDiv);

  return containerDiv;
};

/** 게시판 갱신시 갱신된 게시글/페이징 목록 렌더링
 * @author Jiho
 * @param {number} boardType 게시판 종류 번호
 * @param page 페이지 번호(cp)
 */
const renderBoardList = async (boardType, page) => {

  // 중앙 게시글 목록 div 생성
  const mainContent = createDiv("main-content");
  const updatedBoardContainer = createDiv("board-list");

  // page 값에 따라 요청 변경
  let queryString;
  if(page == null) queryString = "";
  else queryString = `?cp=${page}`;

  // ajax를 통해 비동기로 회원별 게시판 목록 각 요소 클릭시 boardList & pagination 구해옴
  const resp = await fetch(`/board/${boardType}${queryString}`);
  const map = await resp.json();

  // map 내부에 있는 pagination & boardList 선언
  const pagination = map.pagination;
  const boardList = map.boardList;

  // 게시글이 없는 경우
  if(boardList == null) {
    const span = document.createElement("span");
    span.innerText = "게시글이 존재하지 않습니다.";

    updatedBoardContainer.append(span);
    mainContent.append(updatedBoardContainer);
    
    mainContent.append(createBoardFooter(pagination));

    document.querySelector(".main-content").replaceWith(mainContent);

    return;
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

    const iconRead = document.createElement("span");
    iconRead.classList.add("fa-solid", "fa-book-open-reader");
    const readCount = document.createElement("span");
    readCount.innerText = board.readCount;

    boardReads.append(iconRead, readCount);
    boardInfo.append(boardReads);

    // 게시글 하나에 모든 내용 넣기
    boardItem.append(boardWrap, boardInfo);

    // 게시글 목록 div에 게시글 하나 넣기
    updatedBoardContainer.append(boardItem);
  }

  // 게시글 목록 추가
  mainContent.append(updatedBoardContainer);

  mainContent.append(createBoardFooter(pagination));

  // 기존 중앙 컨텐츠 영역 교체
  document.querySelector(".main-content").replaceWith(mainContent);
}

/** 현재 url을 통해 현재 선택된 게시판 & 페이지로 게시글 목록 불러오기
 * @author Jiho
 * @param boardType 게시판 종류
 */
const loadBoardList = (boardType) => {
  // cp 값으로 렌더링 (새로고침 가능)
  const cp = searchCp();
  renderBoardList(boardType, cp).catch(console.error);
}

// 좌측 게시판 목록 선택
// 게시판 목록 div 태그들을 차례대로 선택, 안에 있는 th:data-board-code 로부터 boardCode를 얻어와서 click 이벤트 추가
document.querySelectorAll(".board-type-item").forEach(boardTypeItem => {

  boardTypeItem.addEventListener("click", () => {

    // 현재 게시판 종류 번호 갱신
    boardCode = boardTypeItem.dataset.boardCode;
    // boardCode 반영해 url 갱신
    history.pushState({}, '', boardCode);

    // 게시글 목록 갱신
    renderBoardList(boardCode, null).catch(console.error);
  });
});

// 뒤로가기 실행 시
window.addEventListener("popstate", () => {
  // 현재 게시판 종류 번호를 이전 게시판 종류 번호로 바꿈
  // 페이지에 맞게 게시글 목록을 다시 불러옴
  boardCode = location.pathname.split('/')[3];
  loadBoardList(boardCode);
});

loadBoardList(boardCode);