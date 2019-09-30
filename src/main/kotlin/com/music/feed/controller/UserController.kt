package com.music.feed.controller

import com.music.feed.responses.RequestResponse
import com.music.feed.domain.auth.User
import com.music.feed.responses.TokenResponse
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImpl
import com.music.feed.service.auth.interfaces.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*

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

        securityService.autoLogin(userForm.email, userForm.password)
        return ResponseEntity(RequestResponse("Session started", 200), HttpStatus.OK)
    }

    @GetMapping(value =["/login/{token}"])
    @ResponseBody
    fun loginToken(@PathVariable("token") token:String): ResponseEntity<Any>{
        val user = userService.findByLoginToken(token)
        if(!user.isPresent){
            return ResponseEntity("Non valid token", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        securityService.autoLogin(user.get().email, user.get().password)
        return ResponseEntity(RequestResponse("Session started", 200), HttpStatus.OK)
    }

    @PostMapping(value = ["/token"])
    @ResponseBody
    fun requestToken(@RequestBody userForm :UserForm , bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val findUser = userService.findByEmail(userForm.email)
        if(findUser.isPresent){
            findUser.get().loginToken = UUID.randomUUID().toString()
        }
        else{
            return ResponseEntity(RequestResponse("User not found", 500), HttpStatus.OK)
        }

        userService.save(findUser.get())
        securityService.autoLogin(userForm.email, userForm.password)

        return ResponseEntity(TokenResponse("Here is your token", 200,
                findUser.get().loginToken.toString()), HttpStatus.CREATED)
    }

    @PostMapping(value = ["/registration"])
    @ResponseBody
    fun registration(@RequestBody userForm: UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        val user = User()
        user.email = userForm.email
        user.password = userForm.password
        user.loginToken =  UUID.randomUUID().toString()

        userService.save(user)
        securityService.autoLogin(userForm.email, userForm.password)

        return ResponseEntity(TokenResponse("Account created", 201,
                user.loginToken.toString()), HttpStatus.CREATED)
    }
}