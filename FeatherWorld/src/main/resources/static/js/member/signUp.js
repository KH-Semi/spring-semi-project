
        
  // 폼 유효성 검사
        const form = document.getElementById('signupForm');
        const email = document.getElementById('email');
        const authkey = document.getElementById('authkey');
        const name = document.getElementById('name');
        const phone = document.getElementById('phone');
        const password = document.getElementById('password');
        const passwordCheck = document.getElementById('passwordCheck');
        
        // 이메일 검증
        email.addEventListener('input', function() {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const validationMessage = this.nextElementSibling;
            
            if (!emailRegex.test(this.value)) {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
        });
        
        // 인증번호 검증
        authkey.addEventListener('input', function() {
            const validationMessage = this.nextElementSibling;
            
            if (this.value.trim() === '') {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
        });
        
        // 이름 검증
        name.addEventListener('input', function() {
            const validationMessage = this.nextElementSibling;
            
            if (this.value.trim() === '') {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
        });
        
        // 전화번호 검증
        phone.addEventListener('input', function() {
            const phoneRegex = /^[0-9]{3}[0-9]{3,4}[0-9]{4}$/;
            const validationMessage = this.nextElementSibling;
            
            if (!phoneRegex.test(this.value)) {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
                validationMessage.textContent = '유효한 전화번호를 입력해주세요 (예: 01012345678)';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
        });
        
        // 비밀번호 검증
        password.addEventListener('input', function() {
            const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%])[A-Za-z\d!@#$%]{8,12}$/;
            const validationMessage = this.nextElementSibling;
            
            if (!passwordRegex.test(this.value)) {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
            
            // 비밀번호 확인 필드도 체크
            if (passwordCheck.value !== '') {
                if (this.value !== passwordCheck.value) {
                    passwordCheck.classList.add('error');
                    passwordCheck.classList.remove('valid');
                    passwordCheck.nextElementSibling.style.display = 'block';
                } else {
                    passwordCheck.classList.remove('error');
                    passwordCheck.classList.add('valid');
                    passwordCheck.nextElementSibling.style.display = 'none';
                }
            }
        });
        
        // 비밀번호 확인 검증
        passwordCheck.addEventListener('input', function() {
            const validationMessage = this.nextElementSibling;
            
            if (this.value !== password.value) {
                this.classList.add('error');
                this.classList.remove('valid');
                validationMessage.style.display = 'block';
            } else {
                this.classList.remove('error');
                this.classList.add('valid');
                validationMessage.style.display = 'none';
            }
        });
        
        // Get Authkey 버튼 클릭
        document.getElementById('getAuthkey').addEventListener('click', function() {
            if (email.value.trim() === '') {
                alert('이메일을 먼저 입력해주세요.');
                email.focus();
                return;
            }
            
            alert('인증번호가 발송되었습니다. 이메일을 확인해주세요.');

            fetch("/email/signUp" , {
                method : "POST" ,
                headers : {"Content-type" : "application/json"},
                body: email.value
            })
            .then(resp => resp.text())
            .then(count => {
                if(count == 1){
                    console.log("인증번호 발송");
                }else{
                    console.log("발송 실패")
                }
            })
        });

        
        // 주소 검색 버튼 클릭
       document.getElementById('searchAddress').addEventListener('click', function() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('zipCode').value = data.zonecode;
            document.getElementById('address').value = data.roadAddress || data.jibunAddress;
            document.getElementById('addressDetail').focus();
        }
    }).open();
});
             
    
            // 실제 구현시 카카오 주소 API 등을 사용하여 구현
        
        
        // Main 버튼 클릭
        document.getElementById('mainBtn').addEventListener('click', function() {
            if (confirm('메인 페이지로 이동하시겠습니까? 입력한 정보는 저장되지 않습니다.')) {
                alert('메인 페이지로 이동합니다.');
                // 실제 구현시 메인 페이지 URL로 이동
                location.href="/";
            }
            
        });
        
        // 회원가입 폼 제출
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // 모든 필수 입력 필드 확인
            const requiredFields = [email, authkey, name, phone, password, passwordCheck];
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (field.value.trim() === '') {
                    field.classList.add('error');
                    field.nextElementSibling.style.display = 'block';
                    isValid = false;
                }
            });
            
            // 비밀번호 일치 확인
            if (password.value !== passwordCheck.value) {
                passwordCheck.classList.add('error');
                passwordCheck.nextElementSibling.style.display = 'block';
                isValid = false;
            }
            
            if (isValid) {
                alert('회원가입이 완료되었습니다!');
                // 실제 구현시 서버로 데이터 전송 로직 추가
            } else {
                alert('입력 정보를 확인해주세요.');
            }
        });



       