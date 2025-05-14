//!-- Daum Postcode API 
   
document.getElementById('addressSearchBtn').addEventListener('click', function() {
    
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('postalCode').value = data.zonecode;
                    document.getElementById('roadAddress').value = data.roadAddress || data.jibunAddress;
                    document.getElementById('addressDetail').focus();
                }
            }).open();
        });
        
const chckObj = {
  "memberEmail" : false,
  "a"
}



        /*
        document.getElementById('idConfirmBtn').addEventListener('click', function() {
            const username = document.getElementById('username').value;
            if (!username) {
                alert('아이디를 입력해주세요.');
                return;
            }
            
            // ID 중복 확인 요청 구현
            fetch('/member/checkId', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: username }),
            })
            .then(response => response.json())
            .then(data => {
                if (data.available) {
                    alert('사용 가능한 아이디입니다.');
                } else {
                    alert('이미 사용 중인 아이디입니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
        });
        */