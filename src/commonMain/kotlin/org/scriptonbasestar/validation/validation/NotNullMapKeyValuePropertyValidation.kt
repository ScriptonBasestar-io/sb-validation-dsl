package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.ValidationResult
import kotlin.reflect.KProperty1

internal class NotNullMapKeyValuePropertyValidation<T, K, V>(
    private val property: KProperty1<T, Map<K, V>>,
    private val validation: Validation<Map<K, V>>
) : Validation<T> {
    override fun validate(value: T): ValidationResult<T> {
        val propertyValue = property(value)
        return validation(propertyValue).mapError { ".${property.name}$it" }.map { value }
    }
}
