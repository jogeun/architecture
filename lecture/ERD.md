# ERD
[
![ERD](https://github.com/jogeun/week2-archit/issues/1#issue-2565290784)

> JPA와 TEST 코드에 아직 익숙하지 않아 시간이 부족하기도 하고,
> 특별한 요구사항이 없기 때문에, 최소한의 칼럼만 사용했습니다 ...!
>
## Member

사용자 정보를 저장하는 테이블이다.

- member_id (BIGINT): 사용자의 고유 식별자 (Primary Key, Auto Increment)
- name (VARCHAR(255)): 사용자 이름

> 회원가입/로그인 기능은 미구현이지만, 사용자는 개별적인 도메인이라 생각하기 때문에 분리했다.

## Lecture

특강 정보를 저장하는 테이블이다.

- lecture_id (LONG): 특강의 고유 식별자 (Primary Key, Auto Increment)
- title (VARCHAR(255)): 특강 제목
- letcurer (VARCHAR(255)): 강연자
- capacity (LONG): 선착순 인원 (defualt = 30)

> 특강도 개별적인 도메인이라 생각하기 때문에 분리했다.

## Enrollment

특강 등록 정보를 저장하는 테이블이다.

- id (LONG): 특강 등록의 고유 식별자 (Primary Key, Auto Increment)
- member_id (LONG): 멤버 ID (Member 테이블의 id를 참조)
- lecture_id (LONG): 특강 ID (Lecture 테이블의 id를 참조)
- createdAt (DATETIME): 생성 시간

> 참조로 인해, 다른 테이블의 정보를 가지고올 수 있으니 별도의 칼럼은 추가하지 않았다.

