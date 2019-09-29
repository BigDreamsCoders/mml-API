package com.music.feed.controller

import com.music.feed.domain.auth.User
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImpl
import com.music.feed.service.auth.UserServiceImpl
import com.music.feed.service.auth.interfaces.SecurityService
import com.music.feed.service.auth.interfaces.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse

@RestController
@RequestMapping("v1/api/user")
class UserController{
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var securityService: SecurityServiceImpl

    @PostMapping(value = ["/login"])
    @ResponseBody
    fun loginProcess(@RequestBody userForm :UserForm , bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        securityService.autoLogin(userForm.username, userForm.password)
        return ResponseEntity(userForm, HttpStatus.OK)
    }

    @PostMapping(value = ["/registration"])
    @ResponseBody
    fun registration(@RequestBody userForm: UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        var result = userService.findByUsername(userForm.username)
        if(result.isPresent){
            return ResponseEntity("Username already in use", HttpStatus.CONFLICT)
        }

        val user = User()
        user.email = userForm.username
        user.password = userForm.password

        securityService.autoLogin(userForm.username, userForm.password)

        userService.save(user)

        return ResponseEntity("Account created", HttpStatus.CREATED)
    }
}