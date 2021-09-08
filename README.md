# 와플스튜디오 SpringBoot Seminar[1] 과제

### 궁금했던 점
1. Email Uniqueness를 실제로 sql에 던져보고 에러가 나면(```DataIntegrityViolationException```) ControllerAdvice에서 잡았는데,
Dto에 custom annotation (ex. ```@UniqueEmail```)을 달아서 앞에서 validation하는 것이 더 바람직한가?
2. django의 ```on_dealte=cascade.SET_NULL```과 같은 기능이 spring에도 있는가?