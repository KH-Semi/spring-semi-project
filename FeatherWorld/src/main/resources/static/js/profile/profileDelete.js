function validateForm() {
  const pw = document.getElementById("memberPw").value.trim();
  const pwCheck = document.getElementById("memberPwCheck").value.trim();

  if (pw.length === 0) {
    alert("비밀번호를 입력해주세요.");
    return false;
  }

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
