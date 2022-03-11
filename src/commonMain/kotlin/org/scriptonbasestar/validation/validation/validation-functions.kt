package org.scriptonbasestar.validation.validation

import org.scriptonbasestar.validation.result.Invalid
import org.scriptonbasestar.validation.result.Valid
import org.scriptonbasestar.validation.result.ValidationResult

internal fun <R> ValidationResult<R>.mapError(keyTransform: (String) -> String): ValidationResult<R> {
    return when (this) {
        is Valid -> this
        is Invalid -> Invalid(
            this.internalErrors.mapKeys { (key, _) ->
                keyTransform(key)
            }
        )
    }
}

internal fun <R> ValidationResult<R>.combineWith(other: ValidationResult<R>): ValidationResult<R> {
    return when (this) {
        is Valid -> return other
        is Invalid -> when (other) {
            is Valid -> this
            is Invalid -> {
                Invalid(
                    (this.internalErrors.toList() + other.internalErrors.toList())
                        .groupBy({ it.first }, { it.second })
                        .mapValues { (_, values) -> values.flatten() }
                )
            }
        }
    }
}
