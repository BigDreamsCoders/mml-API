package com.music.feed.controller

import com.music.feed.domain.RequestResponse
import com.music.feed.domain.auth.User
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImpl
import com.music.feed.service.auth.UserServiceImpl
import com.music.feed.service.auth.interfaces.SecurityService
import com.music.feed.service.auth.interfaces.UserService
import com.music.feed.validator.LoginValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.validation.Validator
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse
import java.util.*

@RestController
@RequestMapping("v1/api/user")
class UserController{
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var securityService: SecurityServiceImpl

    @Autowired
    lateinit var loginValidator : LoginValidator

    @PostMapping(value = ["/login"])
    @ResponseBody
    fun loginProcess(@RequestBody userForm :UserForm , bindingResult: BindingResult): ResponseEntity<Any>{

        loginValidator.validate(userForm, bindingResult)
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        securityService.autoLogin(userForm.email, userForm.password)
        return ResponseEntity(userForm, HttpStatus.OK)
    }

    @PostMapping(value = ["/registration"])
    @ResponseBody
    fun registration(@RequestBody userForm: UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        loginValidator.validate(userForm, bindingResult)
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        val user = User()
        user.email = userForm.email
        user.password = userForm.password
        user.loginToken =  UUID.randomUUID()

        userService.save(user)
        securityService.autoLogin(userForm.email, userForm.password)

        return ResponseEntity(RequestResponse("Account created the token is: ${user.loginToken.toString()}", 201), HttpStatus.CREATED)
    }
}