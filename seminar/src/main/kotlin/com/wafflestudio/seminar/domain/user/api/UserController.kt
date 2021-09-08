package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.os.model.User
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

// Handle Exceptions on CommonControllerAdvice
@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val modelMapper: ModelMapper
) {
    @PostMapping("/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addUser(
        @ModelAttribute @Valid body: UserDto.CreateRequest,
        // bindingResult: BindingResult,
    ): ResponseEntity<UserDto.Response> {
        // TODO Controller layer의 validation과 Service layer validation의 차이
        /*
        if (bindingResult.hasErrors()) {
            val fieldErrors: List<String?> = bindingResult.fieldErrors.stream().map {it.defaultMessage}.collect(Collectors.toList())
            fieldErrors.forEach {println(it)}
            return ResponseEntity.badRequest().build()
        } else {

         */
        val newUser = modelMapper.map(body, User::class.java)
        userService.addUser(newUser)

        val responseBody = modelMapper.map(newUser, UserDto.Response::class.java)
        // return ResponseEntity.ok(userService.saveUser(newUserResponse))
        return ResponseEntity<UserDto.Response>(responseBody, HttpStatus.CREATED)
        //}
    }

    @GetMapping("/me")
    fun getMyUser(
        @RequestHeader("User-Id") userId: Long
    ) : ResponseEntity<UserDto.Response> {
        val me = userService.getUserById(userId)
        val responseBody = modelMapper.map(me, UserDto.Response::class.java)
        return ResponseEntity.ok(responseBody)
    }


    // user delete test용
    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable("id") id: Long
    ): ResponseEntity<String> {
        userService.deleteUserById(id)
        return ResponseEntity.ok().body("Delete Done")
    }
}