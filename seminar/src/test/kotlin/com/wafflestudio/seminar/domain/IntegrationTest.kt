package com.wafflestudio.seminar.domain.user.integration

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import javax.transaction.Transactional

/*
  원래는 integration test도 패키지 분리하는 것이 좋습니다.

  또 Unit test 위주로 작성하고 전체적인 흐름을 integration test에서 하는 것이 좋으나
  Unit test의 경우 각 인원의 서버 상세 구현에 맞춰야 하고
  이후 채점의 편의를 위해 하나의 파일에 모든 테스트케이스를 두는 방식을 사용했습니다.
  Integration test뿐 아니라 Unit test도 작성해주시면 좋겠습니다.
  이슈에 질문을 올리시면 유닛테스트도 아주 간단한 예시 올려보겠습니다.

  실제 과제 채점시에는 배포까지 같이 화인할 예정이므로 해당 테스트 파일을 쓰지 않습니다.
  배포된 사이트의 url만 받아 아래 나와있는 테스트 로직 + a 를 api로 날려 진행할 예정이므로 서버가 잘 동작하도록 유지시켜 주시기 바랍니다.
 */
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
internal class IntegrationTest(
    private val mockMvc: MockMvc,
) {
    @BeforeEach
    fun `회원가입`() {
        signupAsParticipantUser("hankp").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }
        // TODO response body 있는데 왜 noContent?

        signupAsInstructorUser("hanki").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }
    }

    // Test에서 호출한 api들은 실제로 저장이 되면 안되는 데이터들이다.
    // 따라서 모든 테스트케이스에 Transactional annotation 추가
    // 이 경우 하나의 테스트케이스가 끝날 때마다 해당 테스트에서의 동작들이 모두 rollback 된다.
    @Test
    @Transactional
    fun `회원 가입 정상 동작 검증`() {
        signupAsParticipantUser("hankp2").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }
    }

    @Test
    @Transactional
    fun `중복 이메일 가입 불가능 조건 검증`() {
        signupAsParticipantUser("hankp").andExpect {
            status { isConflict() }
        }
    }

    @Test
    @Transactional
    fun `회원 가입 요청 body 오류`() {
        signup(
            """
                {
                    "name": "wrong_role",
                    "password": "password",
                    "email": "wrong@snu.ac.kr",
                    "role": "wrong_role",
                    "university": "서울대학교"
                }
            """.trimIndent()
        ).andExpect { status { isBadRequest() } }
        signup(
            """
                {
                    "name": "no_role",
                    "password": "password",
                    "email": "no_role@snu.ac.kr",
                    "university": "서울대학교"
                }
            """.trimIndent()
        ).andExpect { status { isBadRequest() } }
        signup(
            """
                {
                    "name": "no_role",
                    "password": "password",
                    "email": "no_role@snu.ac.kr",
                    "university": "서울대학교",
                    "year": -1
                }
            """.trimIndent()
        ).andExpect { status { isBadRequest() } }
    }

    @Test
    @Transactional
    fun `정상적 로그인`() {
        login("hankp", "hankp")
            .andExpect {
                status { isNoContent() }
                header { exists("Authentication") }
            }
    }

    @Test
    @Transactional
    fun `비정상적 로그인 옳지 않은 정보`() {
        login("hankp", "wrongPassword")
            .andExpect {
                status { isUnauthorized() }
            }
        login("hankpp", "hankp")
            .andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    @Transactional
    fun `특정 유저 정보 GET`() {
        val authentication = login("hankp", "hankp").andReturn().response.getHeader("Authentication")

        // var result = get("users/2/", authentication) // TODO why doesn't work
        // result.andExpect {
        //     status { isOk() }
        // }
        // compare(result, "la;ksdj;flk") // TODO

        login("hanki", "hanki")
        var result = get("users/me/", authentication)
        result.andExpect {
            status { isOk() }
        }
        // compare(result, "hanki_profile") // TODO

        get("users/100/", authentication).andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @Transactional
    fun `유저 정보 수정`() {
    }

    private fun compare(resultActionsDsl: ResultActionsDsl, data: String) {
    }

    private fun put(body: String): ResultActionsDsl {
        return mockMvc.post("/api/v1/users/") {
            content = (body)
            contentType = (MediaType.APPLICATION_JSON)
            accept = (MediaType.APPLICATION_JSON)
        }
    }

    private fun get(url: String, authentication: String?): ResultActionsDsl {
        // mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/$url").header("Authentication", authentication))
        return mockMvc.get("/api/v1/$url") {
            if (authentication != null) {
                header("Authentication", authentication)
            }
        }
    }

    private fun login(name: String, password: String): ResultActionsDsl {
        return mockMvc.post("/api/v1/users/signin/") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content =
                """
                        {
                            "email": "$name@snu.ac.kr",
                            "password": "$password"
                        }
                """.trimIndent()
        } // .andReturn().response.getHeader("Authentication")
    }

    private fun signupAsInstructorUser(name: String): ResultActionsDsl {
        val body =
            """
                {
                    "password": "$name",
                    "name": "$name",
                    "email": "$name@snu.ac.kr",
                    "role": "instructor",
                    "company": "wafflestudio",
                    "year": "1"
                }
            """.trimIndent()
        return signup(body)
    }

    private fun signupAsParticipantUser(name: String): ResultActionsDsl {
        val body =
            """
                {
                    "password": "$name",
                    "name": "$name",
                    "email": "$name@snu.ac.kr",
                    "role": "participant"
                }
            """.trimIndent()
        return signup(body)
    }

    private fun signup(body: String): ResultActionsDsl {
        return mockMvc.post("/api/v1/users/") {
            content = (body)
            contentType = (MediaType.APPLICATION_JSON)
            accept = (MediaType.APPLICATION_JSON)
        }
    }

    private fun foo() {
        println("foo")
    }

    // 계속 업데이트 예정
}
