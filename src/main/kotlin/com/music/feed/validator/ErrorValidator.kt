package com.music.feed.validator

import com.music.feed.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import java.util.*
import java.util.stream.Collectors

@Component
class ErrorValidator {
    fun verifyBindingResult(bindingResult: BindingResult) : Optional<ErrorResponse>  {
        if (bindingResult.hasErrors()) {
            val problems : List<String> = bindingResult.fieldErrors.stream().map {
                "@" + it.field.toUpperCase() + ":" + it.defaultMessage }.collect(Collectors.toList())

            return Optional.of(ErrorResponse("Can't perform action due to errors in fields format",
                    problems,422))
        }
        return Optional.empty()
    }
}