package org.scriptonbasestar.validation.result

import kotlin.reflect.KProperty1

sealed class ValidationResult<T> {
    abstract operator fun get(vararg propertyPath: Any): List<String>?
    abstract fun <R> map(transform: (T) -> R): ValidationResult<R>
    abstract val errors: ValidationErrors
}

data class Invalid<T>(
    internal val internalErrors: Map<String, List<String>>
) : ValidationResult<T>() {

    override fun get(vararg propertyPath: Any): List<String>? =
        internalErrors[propertyPath.joinToString("", transform = ::toPathSegment)]

    override fun <R> map(transform: (T) -> R): ValidationResult<R> = Invalid(this.internalErrors)

    private fun toPathSegment(it: Any): String {
        return when (it) {
            is KProperty1<*, *> -> ".${it.name}"
            is Int -> "[$it]"
            else -> ".$it"
        }
    }

    override val errors: ValidationErrors by lazy {
        DefaultValidationErrors(
            internalErrors.map { (path, errors) ->
                val errors2: List<ValidationError> = errors.map { PropertyValidationError(path, it) }
                path to errors2
            }.toMap()
        )
    }

    override fun toString(): String {
        return "Invalid(errors=$errors)"
    }
}

data class Valid<T>(val value: T) : ValidationResult<T>() {
    override fun get(vararg propertyPath: Any): List<String>? = null
    override fun <R> map(transform: (T) -> R): ValidationResult<R> = Valid(transform(this.value))
    override val errors: ValidationErrors
        get() = DefaultValidationErrors(emptyMap())
}
