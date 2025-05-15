// ajax를 통해 비동기로 회원별 게시판 목록 각 요소 클릭시 boardList & pagination 구해오기
document.querySelectorAll(".board-type-item:not(:first-child)").forEach(item => {

  item.addEventListener("click", async () => {
    
    const resp = await fetch(`/board/${memberNo}/${item.dataset.boardCode}`);
    const map = await resp.json();

    console.log(map.boardList);
    console.log(map.pagination);
  });
});