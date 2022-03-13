package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationResult

internal class IterableValidation<T>(
    private val validation: Validation<T>
) : Validation<Iterable<T>> {
    override fun validate(value: Iterable<T>): ValidationResult<Iterable<T>> {
        return value.foldIndexed(Valid(value)) { index, result: ValidationResult<Iterable<T>>, propertyValue ->
            val propertyValidation = validation(propertyValue).mapError { "[$index]$it" }.map { value }
            result.combineWith(propertyValidation)
        }
    }
}
