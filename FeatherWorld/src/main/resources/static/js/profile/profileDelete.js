function validateForm() {
  const pw = document.getElementById("memberPw").value;
  const pwCheck = document.getElementById("memberPwCheck").value;

  if (pw !== pwCheck) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  const agree = document.getElementById("agree").checked;
  if (!agree) {
    alert("이용약관에 동의하셔야 탈퇴가 가능합니다.");
    return false;
  }

  return true;
}
