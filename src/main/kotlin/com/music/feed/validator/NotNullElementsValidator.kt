package com.music.feed.validator

import com.music.feed.validator.annotation.NotNullElements
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotNullElementsValidator : ConstraintValidator<NotNullElements, Collection<Any>> {
    override fun isValid(value : Collection<Any>?, context : ConstraintValidatorContext) : Boolean{
        if(value == null){
            return true
        }
        return value.stream().noneMatch{it == null}
    }
}