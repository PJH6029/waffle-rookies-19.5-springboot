package com.wafflestudio.seminar

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.os.model.User
import com.wafflestudio.seminar.domain.os.repository.OperatingSystemRepository
import com.wafflestudio.seminar.domain.os.repository.UserRepository
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.repository.SurveyResponseRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class DataLoader(
    private val operatingSystemRepository: OperatingSystemRepository,
    private val surveyResponseRepository: SurveyResponseRepository,
    private val userRepository: UserRepository,
) : ApplicationRunner {
    // 어플리케이션 동작 시 실행
    override fun run(args: ApplicationArguments) {
        val windows = OperatingSystem(name = "Windows", price = 200000, description = "Most favorite OS in South Korea")
        val macos =
            OperatingSystem(name = "MacOS", price = 300000, description = "Most favorite OS of Seminar Instructors")
        val linux = OperatingSystem(name = "Linux", price = 0, description = "Linus Benedict Torvalds")
        operatingSystemRepository.save(windows)
        operatingSystemRepository.save(macos)
        operatingSystemRepository.save(linux)

        val user1 = User(name = "testName1", email = "test@gmail.com")
        val user2 = User(name = "testName2", email = "test2@gmail.com")
        val user3 = User(name = "testName3", email = "duplicate@gmail.com")
        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        BufferedReader(FileReader(ClassPathResource("data/example_surveyresult.tsv").file)).use { br ->
            br.lines().forEach {
                val rawSurveyResponse = it.split("\t")
                val newSurveyResponse = SurveyResponse(
                    timestamp = LocalDateTime.parse(rawSurveyResponse[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    os = operatingSystemRepository.findByNameEquals(rawSurveyResponse[1]),
                    springExp = rawSurveyResponse[2].toInt(),
                    rdbExp = rawSurveyResponse[3].toInt(),
                    programmingExp = rawSurveyResponse[4].toInt(),
                    major = rawSurveyResponse[5],
                    grade = rawSurveyResponse[6]
                )
                surveyResponseRepository.save(newSurveyResponse)
            }
        }
    }
}
