# 와플스튜디오 SpringBoot Seminar[2] 과제

## Test List
### User 관련 URI
#### POST /api/v1/users/ (authorization 생략)
- [ ] 정상적인 회원가입, response body
- [ ] 중복된 email
- [ ] participant profile 생성 확인
- [ ] instructor profile 생성 확인
- [ ] 빈 body 무시
- [ ] validation: role, year,

#### POST /api/v1/users/signin/ (authorization 생략)
- [ ] 정상적인 로그인, response body
- [ ] 로그인 실패(비번 or email 틀림) 401

#### GET /api/v1/users/{user_id}/
- [ ] 200

#### GET /api/v1/users/me/
- [ ] 200

#### PUT /api/v1/user/me/
- [ ] university 수정(값 or default), 200
- [ ] company 수정, 200
- [ ] year 수정, 200
- [ ] 빈 body 무시
- [ ] participant user 수정의 company 무시
- [ ] instructor 수정의 university 무시
- [ ] validation: year

#### POST /api/v1/user/participant/
- [ ] university O or X, 201, body
- [ ] accepted O or X, 201, body
- [ ] 이미 참여자인 사람이 재등록, 400

#### POST /api/v1/seminars/
- [ ] validation: name, capacity, count, time 필수
- [ ] validation: name 0글자
- [ ] valiadation: capacity, count 양수
- [ ] validation: time format
- [ ] validation: online with default
- [ ] 성공적 추가, 201
- [ ] participant가 요청, 403
- [ ] 이미 다른 seminar의 instructor가 요청, 403

#### PUT /api/v1/seminars/{seminar_id}/
- [ ] 빈 body 무시
- [ ] 성공적 수정
- [ ] capacity 작은 값, 400
- [ ] invalid seminar id, 404
- [ ] partcipant가 요청, 403
- [ ] 담당자가 아닌 instructor가 요청, 403

#### GET /api/v1/seminar/{seminar_id}/
- [ ] invalid seminar id, 404
- [ ] 성공적 get

#### GET /api/v1/seminar/
- [ ] 전체 seminar 가져오기
- [ ] name 체크(결과 1개 이상, 0개)
- [ ] order 체크(x, 이상한 값 무시, earliest)

#### POST /api/v1/seminar/{seminar_id}/user/me/
- [ ] invalid seminar id
- [ ] validation: role
- [ ] unaccepted participant의 403
- [ ] 가득찬 세미나 participant 등록요청, 400
- [ ] 다른 seminar의 instructor가 instructor 요청, 400
- [ ] 해당 세미나에 참여한 유저가 요청, 400
- [ ] 성공적 처리, body

#### DELETE /api/v1/seminars/{seminar_id}/user/me/
- [ ] invalid seminar id
- [ ] 정상적 처리, is_active와 dropped_at 확인
- [ ] 중도 포기한 세미나 재등록 실패, 400

#### 로그인, 회원가입 이외 모든 URI
- [ ] 로그인 x인 상태에서 401 