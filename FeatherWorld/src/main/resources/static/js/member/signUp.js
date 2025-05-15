// signUp.js
document.addEventListener('DOMContentLoaded', function() {
    // 입력 필드
    const userId = document.getElementById('userId');
    const authKey = document.getElementById('authKey');
    const name = document.getElementById('name');
    const phone = document.getElementById('phone');
    const password = document.getElementById('password');
    const passwordCheck = document.getElementById('passwordCheck');
    const addressDetail = document.getElementById('addressDetail');
    
    // 메시지 표시용 요소
    const userIdMessage = document.getElementById('userIdMessage');
    const authKeyMessage = document.getElementById('authKeyMessage');
    const nameMessage = document.getElementById('nameMessage');
    const phoneMessage = document.getElementById('phoneMessage');
    const passwordMessage = document.getElementById('passwordMessage');
    const passwordCheckMessage = document.getElementById('passwordCheckMessage');
    const addressDetailMessage = document.getElementById('addressDetailMessage');
    
    // 버튼 요소
    const getAuthKeyBtn = document.getElementById('getAuthKey');
    const authKeyConfirmBtn = document.getElementById('authKeyConfirm');
    const searchAddressBtn = document.getElementById('searchAddress');
    const mainBtn = document.getElementById('mainBtn');
    const signupBtn = document.getElementById('signupBtn');
    
    // 유효성 검사를 위한 정규식
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/; // 이메일 형식
    const phoneRegex = /^01[0|1|6|7|8|9][0-9]{7,8}$/; // 하이픈 없는 전화번호 형식
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%])[A-Za-z\d!@#$%]{8,12}$/; // 비밀번호 형식
    
    // 유효성 검사 함수들
    function validateEmail() {
        if (userId.value.trim() === '') {
            userIdMessage.textContent = '이메일을 입력해주세요.';
            userIdMessage.className = 'validation-message invalid';
            return false;
        } else if (!emailRegex.test(userId.value)) {
            userIdMessage.textContent = '유효한 이메일 형식이 아닙니다.';
            userIdMessage.className = 'validation-message invalid';
            return false;

        } else {
            fetch("/member/checkEmail?memberEmail="+userId)
           .then(resp => resp.text())
            .then(count =>{
                  if(count == 1){// 중복
                  userIdMessage.innerText = "이미 사용중인 이메일 입니다";
                 emailMessage.classList.add("error")  
                  userIdMessage.className = 'validation-message invalid';
                 return false;
            }})
            
            userIdMessage.textContent = '입력값 정상';
            userIdMessage.className = 'validation-message valid';
            return true;

        }
    }
    
    function validateAuthKey() {
        if (authKey.value.trim() === '') {
            authKeyMessage.textContent = '인증번호를 입력해주세요.';
            authKeyMessage.className = 'validation-message invalid';
            return false;
        } else {
            authKeyMessage.textContent = '인증번호 확인이 필요합니다.';
            authKeyMessage.className = 'validation-message invalid';
            return false; // 버튼으로 확인이 필요함
        }
    }
    
    function validateName() {
        if (name.value.trim() === '') {
            nameMessage.textContent = '이름을 입력해주세요.';
            nameMessage.className = 'validation-message invalid';
            return false;
        } else {
            nameMessage.textContent = '입력값 정상';
            nameMessage.className = 'validation-message valid';
            return true;
        }
    }
    
    function validatePhone() {
        const phoneValue = phone.value;
        
        if (phoneValue.trim() === '') {
            phoneMessage.textContent = '전화번호를 입력해주세요.';
            phoneMessage.className = 'validation-message invalid';
            return false;
        } else if (!phoneRegex.test(phoneValue)) {
            phoneMessage.textContent = '유효한 전화번호 형식이 아닙니다.';
            phoneMessage.className = 'validation-message invalid';
            return false;
        } else {
            phoneMessage.textContent = '입력값 정상';
            phoneMessage.className = 'validation-message valid';
            return true;
        }
    }
    
    function validatePassword() {
        if (password.value === '') {
            passwordMessage.textContent = '비밀번호를 입력해주세요.';
            passwordMessage.className = 'validation-message invalid';
            return false;
        } else if (!passwordRegex.test(password.value)) {
            passwordMessage.textContent = '영문, 숫자, 특수문자(!@#$%)를 포함한 8~12자';
            passwordMessage.className = 'validation-message invalid';
            return false;
        } else {
            passwordMessage.textContent = '입력값 정상';
            passwordMessage.className = 'validation-message valid';
            return true;
        }
    }
    
    function validatePasswordCheck() {
        if (passwordCheck.value === '') {
            passwordCheckMessage.textContent = '비밀번호 확인을 입력해주세요.';
            passwordCheckMessage.className = 'validation-message invalid';
            return false;
        } else if (password.value !== passwordCheck.value) {
            passwordCheckMessage.textContent = '비밀번호가 일치하지 않습니다.';
            passwordCheckMessage.className = 'validation-message invalid';
            return false;
        } else {
            passwordCheckMessage.textContent = '입력값 정상';
            passwordCheckMessage.className = 'validation-message valid';
            return true;
        }
    }
    
    // 상세주소 유효성 검사
    function validateAddressDetail() {
        // 우편번호와 도로명 주소가 입력되어 있는지 확인
        const zipcode = document.getElementById('zipcode').value;
        const roadAddress = document.getElementById('roadAddress').value;
        
        if (zipcode && roadAddress) {
            // 주소 검색이 완료된 상태에서 상세주소 검사
            if (addressDetail.value.trim() === '') {
                addressDetailMessage.textContent = '상세 주소를 입력해주세요.';
                addressDetailMessage.className = 'validation-message invalid';
                return false;
            } else {
                addressDetailMessage.textContent = '입력값 정상';
                addressDetailMessage.className = 'validation-message valid';
                return true;
            }
        } else {
            // 주소 검색이 아직 안된 상태
            if (addressDetail.value.trim() !== '') {
                addressDetailMessage.textContent = '먼저 주소 검색을 해주세요.';
                addressDetailMessage.className = 'validation-message invalid';
                return false;
            }
            return true; // 주소 입력이 선택 사항인 경우
        }
    }
    
    // 이벤트 리스너 - 입력 및 포커스 아웃 시 모두 검증
    userId.addEventListener('input', validateEmail);
    userId.addEventListener('blur', validateEmail);
    
    authKey.addEventListener('input', validateAuthKey);
    authKey.addEventListener('blur', validateAuthKey);
    
    name.addEventListener('input', validateName);
    name.addEventListener('blur', validateName);
    
    phone.addEventListener('input', validatePhone);
    phone.addEventListener('blur', validatePhone);
    
    password.addEventListener('input', validatePassword);
    password.addEventListener('blur', validatePassword);
    
    // 비밀번호 확인 - 비밀번호 변경 시에도 검증
    passwordCheck.addEventListener('input', validatePasswordCheck);
    passwordCheck.addEventListener('blur', validatePasswordCheck);
    password.addEventListener('input', function() {
        if (passwordCheck.value) {
            validatePasswordCheck();
        }
    });
    
    // 상세주소 이벤트 리스너
    addressDetail.addEventListener('input', validateAddressDetail);
    addressDetail.addEventListener('blur', validateAddressDetail);
    
    // 버튼 기능 구현
    getAuthKeyBtn.addEventListener('click', function() {
        if (validateEmail()) {
            alert('인증번호가 발송되었습니다.');
            authKeyMessage.textContent = '인증번호를 입력해주세요.';
            authKeyMessage.className = 'validation-message invalid';
        } else {
            alert('유효한 이메일을 입력해주세요.');
        }
    });
    
    authKeyConfirmBtn.addEventListener('click', function() {
        if (authKey.value.trim() === '') {
            authKeyMessage.textContent = '인증번호를 입력해주세요.';
            authKeyMessage.className = 'validation-message invalid';
        } else {

            // 실제 구현 시에는 서버와 검증
            authKeyMessage.textContent = '입력값 정상';
            authKeyMessage.className = 'validation-message valid';
            alert('인증이 완료되었습니다.');
        }
    });
    
    // 주소 관련 기능
    searchAddressBtn.addEventListener('click', function() {
        // Daum 우편번호 서비스를 활용한 주소 검색
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색 결과 처리
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById('roadAddress').value = data.roadAddress || data.jibunAddress;
                
                // 주소 검색 후 상세주소로 포커스 이동
                document.getElementById('addressDetail').focus();
                
                // 상세주소가 필수항목임을 알리는 메시지
                addressDetailMessage.textContent = '상세 주소를 입력해주세요.';
                addressDetailMessage.className = 'validation-message invalid';
            }
        }).open();
    });
    
    mainBtn.addEventListener('click', function() {
        window.location.href = '/main';
    });
    
    // 폼 제출
    document.getElementById('signupForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // 모든 필드 유효성 검사
        const isEmailValid = validateEmail();
        const isAuthKeyValid = authKeyMessage.className.includes('valid');
        const isNameValid = validateName();
        const isPhoneValid = validatePhone();
        const isPasswordValid = validatePassword();
        const isPasswordCheckValid = validatePasswordCheck();
        const isAddressDetailValid = validateAddressDetail();
        
        if (isEmailValid && isAuthKeyValid && isNameValid && isPhoneValid && 
            isPasswordValid && isPasswordCheckValid && isAddressDetailValid) {
            alert('회원가입이 완료되었습니다!');
            // 실제 구현 시에는 서버로 폼 제출
        } else {
            alert('모든 필수 항목을 올바르게 입력해주세요.');
        }
    });
});