# Mybrary

### 🏆삼성 청년 SW아카데미(SSAFY) 10th 공통 프로젝트 최우수상(1위)🏆

## ✅ 프로젝트 진행 기간

### 2024.01.08 ~ 2024.02.16(6주)

## **✅ 프로젝트 소개**

**🚩 서비스 한줄 소개**

```bash
라이브러리 형식의 SNS 서비스
"게시물로 책을 엮고, 책을 모아 나만의 공간을 만드는 차세대 SNS ‘Mybrary’"
```

## ✅ 멤버소개

![팀원소개](/uploads/157b13d2b7c730939b477521626cd91f/팀원소개.PNG)


## ✅ 기술스택

<table>
    <tr>
        <td><b>Back-end</b></td>
        <td><img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/MariaDB-4479A1?style=flat-square&logo=MariaDB&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-59666C?style=flat-square&logo=Hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradle-C71A36?style=flat-square&logo=Gradle&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=
Node.js&logoColor=white"/>


</td>
    </tr>
    <tr>
    <td><b>Front-end</b></td>
    <td>
<img src="https://img.shields.io/badge/Npm-CB3837?style=flat-square&logo=Npm&logoColor=white"/>
<img src="https://img.shields.io/badge/Node-339933?style=flat-square&logo=Node.js&logoColor=white"/>
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=white"/>



<img src="https://img.shields.io/badge/Typescript-3178C6?style=flat-square&logo=Typescript&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/JSON-000000?style=flat-square&logo=json&logoColor=white"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/>
    </td>
    </tr>
    <tr>
    <td><b>Infra</b></td>
    <td>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazon aws&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-4479A1?style=flat-square&logo=Docker&logoColor=white"/>
<img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=NGINX&logoColor=white"/>

</td>
    <tr>
    <td><b>Tools</b></td>
    <td>
    <img src="https://img.shields.io/badge/Notion-333333?style=flat-square&logo=Notion&logoColor=white"/>
    <img src="https://img.shields.io/badge/GitLab-FCA121?style=flat-square&logo=GitLab&logoColor=white"/>
<img src="https://img.shields.io/badge/JIRA-0052CC?style=flat-square&logo=JIRA Software&logoColor=white"/>
    </td>
    </tr>
</table>

## ✅ 산출물

### ERD
![공통_B207팀_ERD](/uploads/4cb6f51b0494818fae94e9627908ca15/공통_B207팀_ERD.png)

### 와이어프레임

### 시스템 아키텍처
![image](/uploads/64d1f9583ed71889f582db7bb792560b/image.png)

## ✅ 기능 소개

### 01. 회원가입, 로그인 🧑👩
![01.회원가입_로그인](/uploads/4bd7e94b3f59b3b3b3d504c96e6e8d11/01.회원가입_로그인.gif)

#### 회원가입
- 이메일 인증 : 메일로 받은 인증코드로 이메일 인증
- 비밀번호 유효성 검사 : 영문 대소문자, 숫자, 특수문자 각 1개 이상 조합하여 3-20자
- 이름 유효성 검사 : 한글(자음 또는 모음만 존재하는 것 제외)을 조합해 2-5자
- 닉네임 유효성 검사 : 영문자, 숫자 및 언더바를 포함할 수 있으며 3-10자

#### 로그인
- 이메일과 비밀번호로 로그인
<br>

### 02. 마이브러리 설명서 📃 / 내 정보 설정 ⚙
![02._신규회원기본책_설정화면](/uploads/a65ebbb3c1ba08ee09f80606f24218c0/02._신규회원기본책_설정화면.gif)

#### 마이브러리 설명서
- 신규회원은 기본 카테고리 3개, 기본 책 1권을 생성해 둠
- 기본책 안에 마이브러리 설명서 페이퍼를 꽂아둠
- 위 기능들로 마이브러리 서비스를 이용하는데에 용이하게 함

#### 설정
- 계정설정 : 프로필이미지 변경, 닉네임 변경, 한줄 소개 변경, 비밀번호 변경
- 알림설정 : 알림 수신 허용/비허용 설정
- 계정탈퇴 : 피드백 메시지와 함께 탈퇴요청을 받아 마이브러리 서비스를 더 개선
<br>

### 03. 마이브러리 꾸미기 🏡 / 팔로워, 팔로잉 목록 🤝

![03._마이브러리꾸미기_팔로우목록](/uploads/3978cd98e981384cc0b153ba2d9d3930/03._마이브러리꾸미기_팔로우목록.gif)

#### 마이브러리 화면 및 꾸미기
- 회원정보 조회 : 닉네임, 이름, 자기소개 표시 생성한 스레드 개수, 책 권수, 팔로워, 팔로잉 수 표시 팔로워, 팔로잉 클릭 시 리스트 모달 나타남
- 문, 책장, 책상, 우체통, 이젤 UI는 각 기능 페이지로 이동
- 마이브러리 커스텀 : 13개의 배경, 6개의 책장, 책상, 이젤 테마 커스텀 가능

#### 팔로워 / 팔로잉 목록
- 팔로워 목록 : 팔로워를 삭제할 수 있고, 내가 팔로우하지 않은 회원은 팔로우 클릭 가능
- 팔로우 목록 : 내가 팔로우 하고 있는 회원 목록 조회
<br>

### 04. 메인피드 화면 📷 / 좋아요, 댓글 기능 💕💬

![04._메인피드_보기좋아요댓글달기](/uploads/aa19a690bb6391b61ce6c8bf86429aba/04._메인피드_보기좋아요댓글달기.gif)

#### 메인피드
- 스크롤로 다음 스레드로 넘어갈 수 있고, 화살표 클릭으로 스레드 내의 다음 페이퍼를 볼 수 있음
- 스레드의 좋아요 수, 댓글 수, 스크랩 수를 확인 가능
- \# 클릭 시 작성된 태그 목록 확인 가능

<br>

- 네비게이션 바<br>마이브러리, 메인피드페이지, PP(채팅)페이지, 검색페이지로 이동 가능

#### 댓글
- 텍스트를 입력하고 5가지의 댓글 색을 선택해 댓글 작성 가능
- 대댓글 수를 클릭해 대댓글 조회, 답글달기를 클릭해 대댓글 작성 가능<br>
  ( 대댓글은 해당 댓글의 색과 동일하게 적용 )
<br>

### 05. 스크랩 🔗 / 구독 📔

![05._메인피드__스크랩_구독](/uploads/23e4654569f4c3ac49e766079fc31688/05._메인피드__스크랩_구독.gif)

#### 스크랩
- 카테고리 별 책 리스트를 통해 스크랩할 책 선택 책의 남은 페이지 수가 표시되며 스크랩할 페이퍼 장수보다 부족한 경우 선택 불가
- 원하는 책이 없을 경우 새 책 생성 가능<br>
( -> 마이브러리 책장에서 이어서 설명 )

#### 구독
- 페이퍼가 포함된 책 목록 또는 다른 사람의 마이브러리 책장을 통해 책 상세 화면으로 이동
- 오른쪽 상단의 내 책장에 담기로 구독 가능
<br>

### 06. 스레드 작성 화면 📝

![06._스레드작성하기](/uploads/d0630e5174f61fa24fcba400ef053439/06._스레드작성하기.gif)

##### 레이아웃
- 1컷 레이아웃 18개, 2컷 레이아웃 19개, 총 37개의 레이아웃으로 다양한 스레드 작성 가능<br>
- 이미지 컷 수 뿐만 이미지와 텍스트 칸의 비율도 다양함<br>

##### 이미지 업로드
- 레이아웃의 비율에 맞는 이미지를 업로드해 게시 가능

##### 본문 작성
- textarea 에디터로 글꼴크기, 글꼴색, 정렬 등을 지정할 수 있음 하이퍼링크 삽입도 가능

##### 페이퍼 추가
- 최대 5개의 페이퍼까지 작성 가능 페이퍼를 이동하면서 페이퍼 작성할 수 있음

##### 태그
- 태그 선택하기를 클릭해 태그 선책 화면으로 전환<br>
- 원하는 태그를 작성해 태그 추가

##### 책 선택
- 해당 스레드를 게시와 동시에 책에 담을 수 있음 ( -> 스크랩하기와 동일 )

##### 공개 설정 및 스크랩허용 설정
- 스레드를 전체공개 / 나만보기 설정 가능
- 스레드 스크랩을 허용 / 비허용 설정 가능 ( 나만보기 설정 시 스크랩은 비허용만 가능 )
<br>

### 07. 스레드 목록 화면 (책상) 📰

![07._내스레드조회](/uploads/b14f3d4b6ceb372142b8e8ece544b1dd/07._내스레드조회.gif)

- 최근 시간 순으로 정렬된 스레드 목록 조회 가능
- 스레드에 해당하는 페이퍼들의 좋아요 수, 댓글 수, 스크랩 수를 합한 개수 표시
<br>

### 08. 카테고리 화면 (책장) 📚

![08._내책장조회](/uploads/1d9a09464a3df2ff99febf9a85cb1257/08._내책장조회.gif)

#### 책장
- 사용자가 정한 순서대로 카테고리 목록이 책장 UI로 표현
- 카테고리 이름과 포함된 책 권수에 따른 책 UI ( 10권 이상일 경우 초과개수로 표시 )

#### 카테고리 추가 / 수정 / 삭제
- 카테고리 추가 : 새 카테고리 추가
- 카테고리 수정 : 카테고리를 드래그해 순서 조정 ( 드래그 후 즉시 반영으로 사용자 친화적 UI )<br>
카테고리 이름 수정 가능
- 카테고리 삭제 : 카테고리에 포함된 책이 한 권도 없을 때 가능

#### 카테고리 별 책 목록 화면
- 사용자가 작성한 책 외에 구독한 책도 포함되어 조회
- 책 제목, 책 표지 이미지, 작성자 정보 표시

#### 책 상세 화면
- 책의 페이퍼가 넘어가는 UI를 적용해 아날로그적 감성을 느낄 수 있는 책 페이지<br>
좋아요, 댓글 기능을 제외해 책 자체의 내용에 집중할 수 있도록 함
- 페이퍼 옵션 : 해당 페이퍼가 포함된 스레드로 이동하기 -> 스레드 단건 조회<br>
해당 페이퍼를 책에서 제거하기
<br>

### 09. 책 생성 / 책 수정 및 삭제 📖

![09._책생성](/uploads/95f32dff00032a29af04361f625801c4/09._책생성.gif)

#### 책 생성
- 책 제목 작성 및 커버 이미지 업로드
- 6개의 책 커버 레이아웃 및 11개의 책 커버 색상으로 책 커스텀 가능
- 책을 꽂을 카테고리 선택

#### 책 수정 및 삭제
- 수정은 이미지 재업로드 불가 외 책 생성과 동일
- 삭제 시 책을 본인 포함 해당 책을 구독한 모든 사용자의 카테고리에서 책 제거되고 삭제
<br>

### 10. 롤링페이퍼 🎨

![10._롤링페이퍼](/uploads/d57906447c0e98b28deaefddbfb64d34/10._롤링페이퍼.gif)

- 익명의 그림 롤링페이퍼로 다른 회원의 마이브러리에 다녀갔다는 흔적을 남길 수 있음
- 여러 사용자가 실시간으로 동기화되어 그림을 그릴 수 있음
- 12가지 펜 색상 지원
- 롤링페이퍼의 주인만 롤링페이퍼를 초기화할 수 있음
<br>

### 11. 알림 🔔

![11._알림](/uploads/30889887b2a6c55171fc74e9bbb5ea1a/11._알림.gif)

- 팔로우 알림🤝
- 댓글 알림💬
- 좋아요 알림💕
- 스크랩 알림🔗
- 구독 알림📚
- 채팅 알림📩
<br>

### 12. Paper Plane (채팅) ✈

![12._채팅](/uploads/990d0a9e2ac817e09d8bc37d55d3ab8c/12._채팅.gif)

- 마지막 메시지 날짜 기준으로 정렬되서 조회
- 실시간으로 채팅이 오면 채팅방 리스트가 갱신되면서 new 라고 표시
<br>

### 13. 검색 🔍

![13._검색](/uploads/ae3e6aab64651ec5e4e274fe6c0e6c53/13._검색.gif)

- 인기검색어 : 엘라스틱 서치를 통해 최근 사용자들에게 가장 많이 검색된 검색어 조회 가능<br>
- 검색 필터 : 스레드, 앨범, 계정 별 검색 가능 엘라스틱 서치로 빠른 성능의 조회 가능
- 엘라스틱 서치로 추천검색어도 나타남
<br>

## ✅ 프로젝트 후기

### 고명성
지난 학기 진행한 프로젝트는 공식적인 팀 프로젝트라기보다는 짧은 기간과 소수 인원으로 인해 제대로 된 협업을 경험하기 어려웠습니다. 하지만, 이번 2학기에는 운 좋게도 훌륭한 팀원들과 함께할 수 있었고, 이를 통해 7주 동안 큰 어려움 없이 프로젝트를 순조롭게 진행할 수 있었습니다. 물론 주제 선정부터 역할 분배, 문서 작성, 프로젝트 설계 및 구현에 이르기까지 어려움이 전혀 없었다고 말하면 거짓이겠지만 그럼에도 불구하고, 팀원 모두가 힘을 모아 난관을 극복했고, 이로 인해 프로젝트를 성공적으로 마칠 수 있었다고 생각합니다. 혼자였다면 도저히 상상할 수 없었던 성과를, 좋은 팀원들과 함께 이룰 수 있어 매우 큰 성취감을 느꼈습니다. 이는 즐거운 경험이기도 했습니다. 짧은 기간 동안 새로운 기술을 배우고, 좋은 동료들을 만나며, 멋진 프로젝트를 통한 제대로 된 협업을 경험할 수 있어 팀원 모두에게 감사의 말을 전하고 싶습니다. 한번 더 기회가 주어진다면, 현재의 팀원들과 함께 더욱 멋진 프로젝트를 진행해보고 싶습니다.

### 박혜선
개발자를 준비한 이후 첫 프로젝트였지만 좋은 팀원들과 컨설턴트님, 코치님들을 만나 프로젝트를 성공적으로 마무리할 수 있었습니다. 기획부터 모두 적극적으로 참여해 시작부터 분위기가 좋았고 1등을 목표로 프로젝트를 시작했습니다. 서로 모르는 것은 물어보고 알려주면서 많이 배울 수 있었고 노션, 게릿 등을 활용하여 협업 능력도 기를 수 있었습니다. 첫 프로젝트라 전체적인 플랜을 짜는데에 미숙해 리팩토링 할 시간이 부족했던 것이 아쉽지만 팀원들 모두 마지막 주 끝까지 개발에 몰두하여 좋은 성적을 거둘 수 있었습니다. 이번 프로젝트에서 배운 것들이 앞으로의 프로젝트에서도 많은 도움이 될 것이라고 생각하고, 7주 동안 팀원들과 즐겁게 생활했던 기억이 오래 남을 것 같습니다.
