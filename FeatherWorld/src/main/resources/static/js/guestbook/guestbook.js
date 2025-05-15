document.addEventListener('DOMContentLoaded', function() {
    const pageItems = document.querySelectorAll('.page-item');
    pageItems.forEach(item => {
        item.addEventListener('click', function() {
            pageItems.forEach(pi => pi.classList.remove('active'));
            this.classList.add('active');
        });
    });

    const menuItems = document.querySelectorAll('.menu-item');
    menuItems.forEach(item => {
        item.addEventListener('click', function() {
            menuItems.forEach(mi => mi.classList.remove('active'));
            this.classList.add('active');
        });
    });

    const writeButton = document.getElementById('write-btn');
    const inputArea = document.getElementById('guestbook-input');
    const entriesContainer = document.getElementById('guestbook-entries');

    writeButton.addEventListener('click', function() {
        const content = inputArea.value.trim();
        if(content) {
            const now = new Date();
            const formattedDate = `${now.getFullYear()}.${(now.getMonth()+1).toString().padStart(2, '0')}.${now.getDate().toString().padStart(2, '0')} ${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;

            const newEntry = document.createElement('div');
            newEntry.className = 'guestbook-entry';
            newEntry.innerHTML = `
                <div class="entry-header">
                    <div class="entry-avatar"><img src="/api/placeholder/40/40" alt="User Avatar"></div>
                    <div class="entry-info">
                        <div class="entry-author">안준성 (${formattedDate})</div>
                    </div>
                </div>
                <div class="entry-content">${content}</div>
                <a href="#" class="delete-btn">Delete</a>
            `;

            const newDeleteBtn = newEntry.querySelector('.delete-btn');
            newDeleteBtn.addEventListener('click', function(e) {
                e.preventDefault();
                if(confirm('정말로 이 메시지를 삭제하시겠습니까?')) {
                    this.closest('.guestbook-entry').remove();
                }
            });

            entriesContainer.insertBefore(newEntry, entriesContainer.firstChild);
            inputArea.value = '';
        } else {
            alert('내용을 입력해주세요.');
        }
    });
});
