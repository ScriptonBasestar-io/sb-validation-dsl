package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationResult

internal class MapValidation<K, V>(
    private val validation: Validation<Map.Entry<K, V>>
) : Validation<Map<K, V>> {
    override fun validate(value: Map<K, V>): ValidationResult<Map<K, V>> {
        return value.asSequence().fold(Valid(value)) { result: ValidationResult<Map<K, V>>, entry ->
            val propertyValidation =
                validation(entry).mapError { ".${entry.key}${it.removePrefix(".value")}" }.map { value }
            result.combineWith(propertyValidation)
        }
    }
}
