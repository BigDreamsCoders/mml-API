package com.music.feed.validator

import com.music.feed.form.UserForm
import com.music.feed.service.auth.interfaces.UserService

import org.springframework.validation.ValidationUtils
import org.springframework.validation.Errors
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Validator


@Component
class LoginValidator : Validator {
    @Autowired
    private lateinit var userService: UserService

    override fun supports(aClass: Class<*>): Boolean {
        return User::class.java == aClass
    }

    override fun validate(o: Any, errors: Errors) {
        val user = o as UserForm

        val foundUser = userService.findByEmailAndPassword(user.email, user.password)
        if (!foundUser.isPresent) {
            errors.rejectValue("login", "Such user doesnt exist")
        }
    }

}