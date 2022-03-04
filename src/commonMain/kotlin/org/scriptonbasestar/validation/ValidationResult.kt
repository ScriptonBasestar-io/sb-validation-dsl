package org.scriptonbasestar.validation

import org.scriptonbasestar.validation.error.ValidationErrors

sealed class ValidationResult<T> {
    abstract operator fun get(vararg propertyPath: Any): List<String>?
    abstract fun <R> map(transform: (T) -> R): ValidationResult<R>
    abstract val erorrs: ValidationErrors
}
