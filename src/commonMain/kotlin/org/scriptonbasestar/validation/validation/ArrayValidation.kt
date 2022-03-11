package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationResult

internal class ArrayValidation<T>(
    private val validation: Validation<T>
) : Validation<Array<T>> {
    override fun validate(value: Array<T>): ValidationResult<Array<T>> {
        return value.foldIndexed(Valid(value)) { index, result: ValidationResult<Array<T>>, propertyValue ->
            val propertyValidation = validation(propertyValue).mapError { "[$index]$it" }.map { value }
            result.combineWith(propertyValidation)
        }
    }
}
