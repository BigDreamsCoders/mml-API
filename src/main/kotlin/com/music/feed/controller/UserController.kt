package com.music.feed.controller

import com.music.feed.responses.RequestResponse
import com.music.feed.responses.TokenResponse
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImp
import com.music.feed.service.auth.UserServiceImp
import com.music.feed.util.token.JwtTokenUtil
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

//Controlador para usuario
@RestController
@RequestMapping("api/v1/user")
class UserController{
    @Autowired
    lateinit var userService: UserServiceImp

    @Autowired
    lateinit var securityService: SecurityServiceImp

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var errorValidator: ErrorValidator

    @PostMapping(value = ["/login"])
    @ResponseBody
    fun loginProcess(@Valid @RequestBody userForm :UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val user = userService.validateUser(userForm.email, userForm.password)
        if(user.isPresent){
            val token = jwtTokenUtil.getJWTToken(userForm.email)
            //user.get().loginToken = token.replace("Bearer ", "")
            val result = TokenResponse ("Session started", 200, token )
            return ResponseEntity(result, HttpStatus.OK)
        }
        return ResponseEntity(RequestResponse("Invalid credentials", 401), HttpStatus.UNAUTHORIZED)
    }

    @PostMapping(value = ["/registration"])
    @ResponseBody
    fun registration(@Valid @RequestBody userForm: UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        // Move to validator
        if(userService.findByEmail(userForm.email).isPresent){
            return ResponseEntity(RequestResponse ("Email address in use", 401 ),
                    HttpStatus.UNAUTHORIZED)
        }

        userService.save(userForm)
        val token = jwtTokenUtil.getJWTToken(userForm.email)

        securityService.autoLogin(userForm.email, userForm.password)

        return ResponseEntity(TokenResponse ("User created", 201, token ),
                HttpStatus.CREATED)
    }

    @GetMapping(value=["/token"])
    @Secured
    @ResponseBody
    fun tokenTest(): ResponseEntity<Any>{
        return ResponseEntity(RequestResponse("The token is still valid", 200),
                HttpStatus.OK)
    }

    @DeleteMapping(value = ["/{email}"])
    @ResponseBody
    fun deleteUser(@PathVariable email : String) : ResponseEntity<Any>{
        if(userService.delete(email)){
        return ResponseEntity(RequestResponse("User deleted", 200),
                HttpStatus.OK)
        }
        return ResponseEntity(RequestResponse("No such user registered by that email", 500),
        HttpStatus.OK)
    }

}