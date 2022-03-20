package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Invalid
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationError
import org.scriptonbasestar.validation.result.ValidationResult

internal class ValidationNode<T>(
    private val constraints: List<Constraint<T>>,
    private val subValidations: List<Validation<T>>
) : Validation<T> {
    override fun validate(value: T): ValidationResult<T> {
        val subValidationResult = applySubValidations(value, keyTransform = { it })
        val localValidationResult = localValidation(value)
        return localValidationResult.combineWith(subValidationResult)
    }

    private fun localValidation(value: T): ValidationResult<T> {
        return constraints
            .filter { !it.test(value) }
            .map { it.hint }
            .let { errors ->
                if (errors.isEmpty()) {
                    Valid(value)
                } else {
                    Invalid(mapOf("" to errors.map { ValidationError("", it, null) }))
                }
            }
    }

    private fun applySubValidations(propertyValue: T, keyTransform: (String) -> String): ValidationResult<T> {
        return subValidations.fold(Valid(propertyValue)) { existingValidation: ValidationResult<T>, validation ->
            val newValidation = validation.validate(propertyValue).mapError(keyTransform)
            existingValidation.combineWith(newValidation)
        }
    }
}
