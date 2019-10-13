package com.music.feed.controller

import com.music.feed.responses.RequestResponse
import com.music.feed.domain.auth.User
import com.music.feed.responses.TokenResponse
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImpl
import com.music.feed.service.auth.UserServiceImpl
import com.music.feed.service.auth.interfaces.UserService
import com.music.feed.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import org.apache.tomcat.jni.User.username
import java.util.stream.Collectors
import org.springframework.security.core.GrantedAuthority
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.authority.AuthorityUtils





@RestController
@RequestMapping("api/v1/user")
class UserController{
    @Autowired
    lateinit var userService: UserServiceImpl

    @Autowired
    lateinit var securityService: SecurityServiceImpl

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @PostMapping(value = ["/login"])
    @ResponseBody
    fun loginProcess(@RequestBody userForm :UserForm , bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val user = userService.validateUser(userForm.email, userForm.password)
        if(user.isPresent){
            val token = jwtTokenUtil.getJWTToken(userForm.email)
            user.get().loginToken = token.replace("Bearer ", "")
            userService.saveNoCrypt(user.get())
            val result = TokenResponse ("Session started", 200, token )
            return ResponseEntity(result, HttpStatus.OK)
        }
        return ResponseEntity(RequestResponse("Invalid credentials", 500), HttpStatus.OK)
    }

    @PostMapping(value = ["/registration"])
    @ResponseBody
    fun registration(@RequestBody userForm: UserForm, bindingResult: BindingResult): ResponseEntity<Any>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(bindingResult.allErrors, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        // Move to validator
        if(userService.findByEmail(userForm.email).isPresent){
            return ResponseEntity(RequestResponse ("Email address in use", 500 ),
                    HttpStatus.INTERNAL_SERVER_ERROR)
        }

        val token = jwtTokenUtil.getJWTToken(userForm.email)
        val user = User()

        user.email = userForm.email
        user.password = userForm.password
        user.loginToken =  token.replace("Bearer ", "")

        userService.save(user)
        securityService.autoLogin(userForm.email, userForm.password)

        return ResponseEntity(TokenResponse ("User created", 201, token ), HttpStatus.CREATED)
    }

    @GetMapping(value=["/token"])
    @ResponseBody
    fun tokenTest(): ResponseEntity<Any>{
        return ResponseEntity(RequestResponse("The token is still valid", 200),
                HttpStatus.OK)
    }

    /*
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
   }*/
}