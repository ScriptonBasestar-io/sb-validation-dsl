package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Invalid
import org.scriptonbasestar.validation.result.ValidationError
import org.scriptonbasestar.validation.result.ValidationResult
import kotlin.reflect.KProperty1

internal class RequiredPropertyValidation<T, R>(
    private val property: KProperty1<T, R?>,
    private val validation: Validation<R>
) : Validation<T> {
    override fun validate(value: T): ValidationResult<T> {
        val propertyValue = property(value)
            ?: return Invalid<T>(
                mapOf(
                    ".${property.name}" to listOf(
                        ValidationError(property.name, "is required", null)
                    )
                )
            )
        return validation(propertyValue).mapError { ".${property.name}$it" }.map { value }
    }
}
