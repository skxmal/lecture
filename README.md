## 강연 신청

### 개발환경

-개발언어 : java

-프레임워크 : spring boot 2.3.4

-RDBMS : mariadb

---

### 데이타 설계

1. LECTURE_TB
  - 강연 정보 테이블

|필드명|유형|설명|
|------|---|---|
|LT_IDX|BIGINT(20)|강연 IDX(PK)|
|LT_NM|VARCHAR(100)|강연자명|
|LT_PLACE|VARCHAR(100)|강연장|
|MAX_REQ|INT(11)|신청인원|
|START_DATE|DATETIME|강연 시간|
|LT_CNN|VARCHAR(300)|강연 내용|

2. APPLY_TB
  - 강연 신청 정보 테이블

|필드명|유형|설명|
|------|---|---|
|APPLY_IDX|BIGINT(20)|신청 테이블 IDX(PK)|
|LT_IDX|BIGINT(20)|강연 IDX|
|EMP_NO|VARCHAR(10)|사번|
|INST_DATE|DATETIME|등록일시|

---

### API 설명

1. BackOffice

|API명|URL|KEY|사용예시|
|------|---|---|---|
|강연 목록(전체 강연 목록)|/api/admin/lecture/lectureList.do|-|/api/admin/lecture/lectureList.do|
|강연 등록|/api/admin/lecture/saveLecture.do|ltNm(강연자), ltPlace(강연장), startDate(강연시간), maxReq(신청인원), ltCnn(강연내용), ltIdx(강연IDX-수정시 사용)|/api/admin/lecture/saveLecture.do?ltNm=안치우&ltPlace=공연장6&startDate=2023-09-12 10:10&maxReq=30&ltCnn=안치우의 공연6|
|강연신청자 목록|/api/admin/lecture/lectureApplyList.do|ltIdx(강연IDX)|/api/admin/lecture/lectureApplyList.do?ltIdx=1|

2. Front

|API명|URL|KEY|사용예시|
|------|---|---|---|
|강연 목록(시작일 전 일주일부터 시작일 1일 이후까지 노출)|/api/lecture/lectureList.do|-|/api/lecture/lectureList.do|
|강연 신청|//api/lecture/applyLecture.do|ltIdx(강연IDX), empNo(사번)|/api/lecture/applyLecture.do?ltIdx=2&empNo=22222|
|신청내역 조회|/api/lecture/applyList.do|empNo(사번)|/api/lecture/applyList.do?empNo=24611|
|신청한 강연 취소|/api/lecture/applyLectureCancel.do|ltIdx(강연IDX), empNo(사번)|/api/lecture/applyLectureCancel.do?ltIdx=2&empNo=22222|
|실시간 인기 강연|/api/lecture/popularLectureList.do|-|/api/lecture/popularLectureList.do|

---

### 기타

1. 단위테스트

swagger로 테스트 하였습니다.

주소 : http://localhost:8080/swagger-ui.html

2. 동시성 이슈

curl을 이용해 동시 신청 시도시 정상 등록 확인 하였습니다.

혹시 몰라서 신청인원을 초과되는 신청자가 등록시 롤백 되는 코드도 추가하였습니다.
