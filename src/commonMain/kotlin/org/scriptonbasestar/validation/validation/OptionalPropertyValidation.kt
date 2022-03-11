package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationResult
import kotlin.reflect.KProperty1

internal class OptionalPropertyValidation<T, R>(
    private val property: KProperty1<T, R?>,
    private val validation: Validation<R>
) : Validation<T> {
    override fun validate(value: T): ValidationResult<T> {
        val propertyValue = property(value) ?: return Valid(value)
        return validation(propertyValue).mapError { ".${property.name}$it" }.map { value }
    }
}
