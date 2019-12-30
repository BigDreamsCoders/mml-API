package com.music.feed.validator.annotation

import com.music.feed.validator.NotBlankElementsValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [NotBlankElementsValidator::class])
@Target(allowedTargets = [AnnotationTarget.FUNCTION,
    AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER,
AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.FIELD])
@Retention(AnnotationRetention.RUNTIME)
annotation class NotBlankElements(
        val message : String = "must not contain blank elements",
        val groups : Array<KClass<out Any>> = [],
        val payload : Array<KClass<out Payload>> = []
)