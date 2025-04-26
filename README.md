KH Semi Project Repogitory
==========================
KH 정보교육원 세미 프로젝트 협업을 위한 레포지토리

<br>

준비사항
--------

> ### 먼저 Repogitory를 clone해서 본인 local에 저장!
- 협업해서 작업하기 위해 원격 Repogitory에만 저장되어 있는 코드를 Local Repogitory로 가져오는 과정
- clone 이후에는 아래 주의사항을 참고하여 함께 협업 진행!

```bash
$ git clone (Repogitory 주소)
```

<br>
<br>

주의사항
--------

> ### master branch에서 절대! 작업이나 push를 하지 않는다. (가장 중요)
- master는 항상 배포 가능해야 하기 때문에, 안정적인 관리를 위한 develop branch를 따로 운영한다.

<br>

> ### develop branch에서도 직접 작업하지 않고 feature branch를 만든 후 merge한다.
- develop 또한 많은 작업들이 동시에 이뤄지기 때문에, 순차적으로 merge 진행!!
- feature branch 작명 규칙은 feature/[기능명] (ex. feature/login)

```bash
# merge 이후, 무조건 코드 최신화 먼저!
$ git pull origin develop

# 'feature' 브랜치를 'develop' 브랜치에서 분기
$ git checkout -b feature/[기능명] develop

# 새로운 기능에 대한 작업 수행 + (add, commit)
# 작업 중이라면 'develop' 브랜치로 이동하면 안 됨!

# 원격 Repogitory에 본인이 local에서 만든 'feature' 브랜치를 올림
$ git push -u origin feature/[기능명]

# github에서 'feature -> develop'(중요!!! master 아님!) Pull Request! 이후 논의해서 순차적으로 merge 진행
# 따로 develop으로 가서 pull 해올 필요없음!

# 'feature/[기능명]' 브랜치 삭제
$ git branch -d feature/[기능명]

# 원격 Repogitory에 올려놓은 'feature' 브랜치 삭제
$ git push origin --delete feature/[기능명]
```

<br>

> ### 동일한 파일을 수정하고 merge하기 전, 충돌(conflict) 발생
- conflict 발생시, 코드 충돌이 발생한 팀원과의 소통 이후 merge
- 최대한 github 내에서 해결하고 불가능한 경우, 아래 내용 참고!

```bash
# 현재 브랜치 상태 저장 (선택사항 - 변경 사항이 있을 경우)
$ git stash

# 최신화 된 원격 Repogitory 정보(commit 내용)를 받아옴
$ git fetch origin

# 최신화 된 'develop' 브랜치 코드들을 'feature' 브랜치에 모두 merge
$ git merge develop

# 협의를 통해 수정된 코드들 반영 & 코드 실행 테스트 완료 + (add, commit)

# 저장했던 변경 사항 복구 (선택 사항)
$ git stash pop

# 수정 코드를 반영해서 'feature' 브랜치에 올림
$ git push origin feature/[기능명]

# 본인 'feature' 브랜치에서 다시 'develop' 브랜치에 Pull Request 작업 완료
```
1. git stash는 현재 작업 중인 내용이 commit 되지 않은 경우 잠깐 보관하는 데 유용
2. 충돌이 없으면 merge 후 바로 새로운 커밋으로 반영됨
3. 충돌이 있으면 수정하고 commit만 해주면 됨