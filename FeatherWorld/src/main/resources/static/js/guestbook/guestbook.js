   // JavaScript 코드
        document.addEventListener('DOMContentLoaded', function() {
            // 페이지 아이템 클릭 이벤트
            const pageItems = document.querySelectorAll('.page-item');
            pageItems.forEach(item => {
                item.addEventListener('click', function() {
                    pageItems.forEach(pi => pi.classList.remove('active'));
                    this.classList.add('active');
                });
            });

            // 메뉴 아이템 클릭 이벤트
            const menuItems = document.querySelectorAll('.menu-item');
            menuItems.forEach(item => {
                item.addEventListener('click', function() {
                    menuItems.forEach(mi => mi.classList.remove('active'));
                    this.classList.add('active');
                });
            });

            // 삭제 버튼 클릭 이벤트
            const deleteButtons = document.querySelectorAll('.delete-btn');
            deleteButtons.forEach(button => {
                button.addEventListener('click', function(e) {
                    e.preventDefault();
                    if(confirm('정말로 이 메시지를 삭제하시겠습니까?')) {
                        this.closest('.guestbook-entry').remove();
                    }
                });
            });

            // 글쓰기 버튼 클릭 이벤트
            const writeButton = document.querySelector('.write-btn');
            const inputArea = document.querySelector('.input-area');
            
            writeButton.addEventListener('click', function() {
                const content = inputArea.value.trim();
                if(content) {
                    const now = new Date();
                    const formattedDate = `${now.getFullYear()}.${(now.getMonth()+1).toString().padStart(2, '0')}.${now.getDate().toString().padStart(2, '0')} ${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
                    
                    // 새 방명록 항목 생성
                    const newEntry = document.createElement('div');
                    newEntry.className = 'guestbook-entry';
                    newEntry.innerHTML = `
                        <div class="entry-header">
                            
                            <div class="entry-info">
                                <div class="entry-author">안준성 (${formattedDate})</div>
                            </div>
                        </div>
                        <div class="entry-content">${content}</div>
                        <a href="#" class="delete-btn">Delete</a>
                    `;
                    
                    // 삭제 버튼에 이벤트 리스너 추가
                    const newDeleteBtn = newEntry.querySelector('.delete-btn');
                    newDeleteBtn.addEventListener('click', function(e) {
                        e.preventDefault();
                        if(confirm('정말로 이 메시지를 삭제하시겠습니까?')) {
                            this.closest('.guestbook-entry').remove();
                        }
                    });
                    
                    // 방명록 맨 위에 새 항목 추가
                    const entriesContainer = document.querySelector('.guestbook-entries');
                    entriesContainer.insertBefore(newEntry, entriesContainer.firstChild);
                    
                    // 입력 필드 비우기
                    inputArea.value = '';
                } else {
                    alert('내용을 입력해주세요.');
                }
            });
        });
      
      
      // 수정 버튼 클릭 시 textarea에 글 복사
const editButtons = document.querySelectorAll('.edit-btn');
editButtons.forEach(button => {
    button.addEventListener('click', function(e) {
        e.preventDefault();
        
        const content = this.closest('.guestbook-entry')
                          .querySelector('.entry-content').innerText;

        const inputArea = document.querySelector('.input-area');
        inputArea.value = content;

        // 혹시 수정 모드 표시하고 싶으면
        document.querySelector('.write-btn').textContent = '수정하기';
    });
});