package org.scriptonbasestar.validation

import org.scriptonbasestar.validation.result.Invalid
import org.scriptonbasestar.validation.result.ValidationResult

fun <T> countFieldsWithErrors(validationResult: ValidationResult<T>) = (validationResult as Invalid).internalErrors.size
fun countErrors(validationResult: ValidationResult<*>, vararg properties: Any) = validationResult.get(*properties)?.size
    ?: 0
