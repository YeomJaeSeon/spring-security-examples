# springBoot-spring-security ex2

## MemberEntity
- Long id
- String username
- String password
- Role role (enum) - 엔티티가 권한을 가지고있는 형태의 예제

## 회원가입
- 관리자인지 일반횐원인지 선택해서 영속화할수있음

## 로그인
- 로그인하면 회원의 Role 필드에 맞게 권한이 부여됨(spring security 역할)

## 페이지
- /admin : admin
- /user/myinfo : user
- 나머지 요청 : 권한필요 X