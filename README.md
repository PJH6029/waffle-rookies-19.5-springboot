# 와플스튜디오 SpringBoot Seminar[2] 과제
## Test List
### User 관련 URI
#### POST /api/v1/users/ (authorization 생략)
- [x] 정상적인 회원가입, response body
- [x] 중복된 email
- [x] participant profile 생성 확인
- [x] instructor profile 생성 확인
- [ ] 빈 body 무시 -> validation에서 걸림!
- [x] validation: role, year
#### POST /api/v1/users/signin/ (authorization 생략)
- [x] 정상적인 로그인, response body
- [x] 로그인 실패(비번 or email 틀림) 401
#### GET /api/v1/users/{user_id}/
- [x] 200
- [x] invalid user id
#### GET /api/v1/users/me/
- [x] 200
#### PUT /api/v1/users/me/
- [x] university 수정(값 or default), 200
- [x] company 수정, 200
- [x] year 수정, 200
- [ ] 빈 body 무시 -> 이걸 무시
- [x] participant user 수정의 company 무시
- [x] instructor 수정의 university 무시
- [x] validation: year
#### POST /api/v1/users/participant/
- [x] university O or X, 201, body
- [x] accepted O or X, 201, body
- [x] 이미 참여자인 사람이 재등록, 400
#### POST /api/v1/seminars/
- [x] validation: name, capacity, count, time 필수
- [x] validation: name 0글자
- [x] valiadation: capacity, count 양수
- [x] validation: time format
- [x] validation: online with default
- [x] 성공적 추가, 201
- [x] participant가 요청, 403
- [x] 이미 다른 seminar의 instructor가 요청, 403
- [ ] instructor update가 안되는 문제
#### PUT /api/v1/seminars/{seminar_id}/
- [ ] 빈 body 무시 -> 무시
- [x] 성공적 수정
- [ ] capacity 작은 값, 400 (is_active도 고려)
- [x] invalid seminar id, 404
- [x] partcipant가 요청, 403
- [x] 담당자가 아닌 instructor가 요청, 403
#### GET /api/v1/seminars/{seminar_id}/
- [x] invalid seminar id, 404
- [x] 성공적 get
#### GET /api/v1/seminars/
- [x] 전체 seminar 가져오기
- [x] name 체크(결과 1개 이상, 0개)
- [x] order 체크(x, 이상한 값 무시, earliest)
#### POST /api/v1/seminars/{seminar_id}/user/me/
- [x] invalid seminar id
- [x] validation: role
- [x] unaccepted participant의 403
- [x] 가득찬 세미나 participant 등록요청, 400
- [x] 다른 seminar의 instructor가 instructor 요청, 400
- [x] 해당 세미나에 참여한 유저가 요청, 400
- [x] 성공적 partcipant 등록 처리, body
- [x] 성공적 instructor 등록 처리, body
#### DELETE /api/v1/seminars/{seminar_id}/user/me/
- [x] invalid seminar id
- [x] 정상적 처리, is_active와 dropped_at 확인
- [x] 중도 포기한 세미나 재등록 실패, 400
#### 로그인, 회원가입 이외 모든 URI
- [x] 로그인 x인 상태에서 401