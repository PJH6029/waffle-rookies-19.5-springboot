package com.wafflestudio.seminar.domain.os.model

import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotBlank
    var name: String? = null,

    @NotBlank
    @Email
    @Column(unique = true)
    var email: String? = null,

    @OneToMany(mappedBy = "user")
    // TODO SurveyResponseDto.Response를 호출할 때, 얘도 같이 serialize됨. 제외하는 방법이 있나?? -> 일단은 Dto 내부 프로퍼티의 타입을 다시 Dto로
    var responses: MutableSet<SurveyResponse>? = HashSet()
)
