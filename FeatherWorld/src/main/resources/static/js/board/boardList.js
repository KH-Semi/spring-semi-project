// 게시판 목록 div 태그들을 차례대로 선택, 안에 있는 th:data-board-code로부터 boardCode를 얻어와서 click 이벤트 추가
document.querySelectorAll(".board-type-item:not(:first-child)").forEach(boardTypeItem => {

  boardTypeItem.addEventListener("click", async () => {

    // 기존 게시글 목록 비워주기
    const boardListContainer = document.querySelector(".board-list");
    boardListContainer.innerHTML = "";

    // ajax를 통해 비동기로 회원별 게시판 목록 각 요소 클릭시 boardList & pagination 구해옴
    const resp = await fetch(`/board/type/${boardTypeItem.dataset.boardCode}`);
    const map = await resp.json();

    // map 내부에 있는 boardList & pagination 선언
    const boardList = map.boardList;

    // 게시글이 하나도 없는 경우
    if(boardList == null) {
      const span = document.createElement("span");
      span.innerText = "게시글이 존재하지 않습니다.";

      boardListContainer.append(span);
      return;
    }

    // 비동기로 가져온 게시글 내용을 꺼내서 html 요소로 대입
    for(board of boardList) {

      // 게시글 하나를 담는 div 생성
      const boardItem = document.createElement("div");
      boardItem.classList.add("board-item");

      // 게시글 썸네일과 게시글 제목/내용을 담는 묶음 div 생성
      const boardWrap = document.createElement("div");
      boardWrap.classList.add("board-wrap");

      // 게시글 썸네일이 있을 경우에만 썸네일 이미지가 들어간 div 넣어줌
      if(board.thumbnail != null) {
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

      // 게시글 목록 div에 게시글 하나 넣기
      boardListContainer.append(boardItem);
    }

    // new URLSearchParams();

    /* 가져온 페이징 목록을 형식에 맞춰 뿌려줘야함
    <div class="pagination" th:if="${pagination != null}" th:object="${pagination}">

      <a th:href="@{/board/{memberNo}(memberNo=${memberNo}, cp=1)}">&lt;&lt;</a>
      <a th:href="@{/board/{memberNo}(memberNo=${memberNo}, cp=*{prevPage})}" class="page-nav">&lt;</a>

      <th:block th:if="*{startPage <= endPage}" th:each="i : *{#numbers.sequence(startPage, endPage)}">
        <a th:href="@{/board/{memberNo}(memberNo=${memberNo}, cp=${i})}"
           th:text="${i}" th:class="${i} == *{currentPage} ? 'current' : ''"></a>
      </th:block>

      <a th:href="@{/board/{memberNo}(memberNo=${memberNo}, cp=*{nextPage})}" class="page-nav">&gt;</a>
      <a th:href="@{/board/{memberNo}(memberNo=${memberNo}, cp=*{maxPage})}">&gt;&gt;</a>

    </div>
    */


  });
});