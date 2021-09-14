# 와플스튜디오 SpringBoot Seminar[1] 과제

**6조 reviewee**

### 궁금했던 점 / 어려웠던 점
- 박정훈
  - django의 ```on_delete=cascade.SET_NULL```과 같은 기능이 spring에도 있나요?
    - 일단은 찾아보니 없어서, ```@PreRemove```를 통해, User 삭제 시 user와 매핑된 SurveyResponse들의 user를 null로 일일이 만들었습니다
- 김지완
  - 과제 스펙 중 SurveyResponseDto에서 major와 grade를 따로 다루지 않은 이유가 궁금합니다!
    - 어차피 SurveyResponse Entity를 save할 때 major, grade의 nullity 체크를 할텐데, 미리 DTO에서 validation을 하는 것이 효율적이지 않을까요?
  - email의 uniqueness를 체크하기 위해 DTO단에 ```Column(unique = true)```를 추가했었는데, column의 uniqueness를 체크하는 대략적인 과정이 궁금합니다
  - ```NotNull```과 같은 제약 조건의 경우, DTO단에서 validation을 하려면 ```@field:```을 추가해야한다고 그러던데([참고자료](https://velog.io/@lsb156/SpringBoot-Kotlin%EC%97%90%EC%84%9C-Valid%EA%B0%80-%EB%8F%99%EC%9E%91%ED%95%98%EC%A7%80-%EC%95%8A%EB%8A%94-%EC%9B%90%EC%9D%B8JSR-303-JSR-380)00))
    - ```@field:```를 따로 하지 않아도 DTO 단에서 400이 왔었고,
    - Entity 단의 validation에서는 500을 던졌습니다(exception catch 전)
    - ```@field:```를 달아주지 않아도 validation이 작동하는 경우가 있나요?
- 이두현
  - Controller 단에서 DTO로 불러와 Entity로 매핑한 값을 함수 안에서 바꿔줘도 안전하다고 볼 수 있는지 궁금합니다.
    - Userid를 헤더로 받고 설문을 진행하는 과정을 implement하는 과정에서 생긴 고민입니다.
  - 예외 처리를 try catch로 할 경우 가독성이 떨어질 수 있어서 ExceptionHandler 같은 것을 사용한다는 [글다을](https://jeong-pro.tistory.com/195) 보았는데 현업에서 어떤 방식을 선호하시는지 궁금합니다.