package com.music.feed.validator

import com.music.feed.annotation.NotBlankElements
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotBlankElementsValidator : ConstraintValidator<NotBlankElements, Collection<Any>>{
    override fun isValid(value : Collection<Any>?, context : ConstraintValidatorContext) : Boolean{
        if(value == null){
            return true
        }
        return value.stream().noneMatch{it == ""}
    }
}