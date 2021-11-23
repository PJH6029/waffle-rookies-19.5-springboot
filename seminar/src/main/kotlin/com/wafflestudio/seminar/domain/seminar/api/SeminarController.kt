package com.wafflestudio.seminar.domain.seminar.api

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/seminars")
class SeminarController(
    private val seminarService: SeminarService,
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSeminar(
        @RequestBody @Valid createRequest: SeminarDto.CreateRequest,
        @CurrentUser user: User,
    ): SeminarDto.Response {
        seminarService.authorizeUnchargedInstructor(user)  // instructor자격 있고, 맡고있는 세미나가 없음
        val newSeminar = seminarService.createSeminar(createRequest, user.instructorProfile!!)
        return SeminarDto.Response(newSeminar)
    }

    @PostMapping("/{seminar_id}/user/me/")
    @ResponseStatus(HttpStatus.CREATED)
    fun joinSeminar(
        @PathVariable("seminar_id") seminarId: Long,
        @RequestBody @Valid joinRequest: UserDto.JoinRequest,
        @CurrentUser user: User,
    ): SeminarDto.Response {
        val seminar = seminarService.getSeminarById(seminarId)
        val role = joinRequest.role

        val savedSeminar: Seminar = if (role == "participant") {
            val participantProfile = seminarService.authorizeParticipant(user)
            seminarService.joinAsParticipant(participantProfile, seminar)
        } else {
            val instructorProfile =
                seminarService.authorizeUnchargedInstructor(user)  // instructor 자격이 있고, 맡고 있는 세미나가 없음
            seminarService.joinAsInstructor(instructorProfile, seminar)
        }
        return SeminarDto.Response(savedSeminar)
    }

    @DeleteMapping("/{seminar_id}/user/me/")
    fun dropSeminar(
        @PathVariable("seminar_id") seminarId: Long,
        @CurrentUser user: User,
    ): SeminarDto.Response {
        val seminar = seminarService.getSeminarById(seminarId)
        if (!seminarService.checkJoinedSeminar(user, seminar)) {
            return SeminarDto.Response(seminar)
        }
        val participantProfile = seminarService.authorizeParticipantOfSeminar(user, seminar)
        return SeminarDto.Response(seminarService.dropSeminar(participantProfile, seminar))
    }

    @PutMapping("/{seminar_id}/")
    fun updateSeminar(
        @Valid @RequestBody updateRequest: SeminarDto.UpdateRequest,
        @PathVariable("seminar_id") seminarId: Long,
        @CurrentUser user: User,
    ): SeminarDto.Response {
        val seminar = seminarService.getSeminarById(seminarId)
        seminarService.authorizeInstructorOfSeminar(user, seminar)
        val updatedSeminar = seminarService.updateSeminar(seminar, updateRequest)
        return SeminarDto.Response(updatedSeminar)
    }

    @GetMapping("/{seminar_id}/")
    fun getSeminar(
        @PathVariable("seminar_id") seminarId: Long
    ): SeminarDto.Response {
        val seminar = seminarService.getSeminarById(seminarId)
        return SeminarDto.Response(seminar)
    }

    @GetMapping("/")
    fun getSeminars(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) order: String?,
    ): ListResponse<SeminarDto.ListResponseElement> {
        val earliest = if (order != null) order == "earliest" else false

        val seminars =
            if (name != null) seminarService.getSeminarsByNameContains(name, earliest = earliest)
            else seminarService.getAllSeminars(earliest = earliest)
        return ListResponse(seminars.map { SeminarDto.ListResponseElement(it) })
    }
}
