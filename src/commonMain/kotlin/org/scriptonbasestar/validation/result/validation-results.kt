package org.scriptonbasestar.validation.result

import kotlin.reflect.KProperty1

sealed class ValidationResult<T> {
    abstract operator fun get(vararg propertyPath: Any): List<ValidationError>?
    abstract fun <R> map(transform: (T) -> R): ValidationResult<R>
    abstract val errors: Map<String, List<ValidationError>>
}

data class Invalid<T>(
    internal val internalErrors: Map<String, List<ValidationError>>
) : ValidationResult<T>() {

    override fun get(vararg propertyPath: Any): List<ValidationError>? =
        internalErrors[propertyPath.joinToString("", transform = ::toPathSegment)]

    override fun <R> map(transform: (T) -> R): ValidationResult<R> = Invalid(this.internalErrors)

    private fun toPathSegment(it: Any): String {
        return when (it) {
            is KProperty1<*, *> -> ".${it.name}"
            is Int -> "[$it]"
            else -> ".$it"
        }
    }

    override val errors: Map<String, List<ValidationError>> by lazy {
        internalErrors.map { (path, errors) ->
            // FIXME path랑  it.path 다른가?
            val errors2: List<ValidationError> = errors.map { ValidationError(path, it.message, it.throwable) }
            path to errors2
        }.toMap()
    }

    override fun toString(): String {
        return "Invalid(errors=$errors)"
    }
}

data class Valid<T>(val value: T) : ValidationResult<T>() {
    override fun get(vararg propertyPath: Any): List<ValidationError>? = null
    override fun <R> map(transform: (T) -> R): ValidationResult<R> = Valid(transform(this.value))
    override val errors: Map<String, List<ValidationError>>
        get() = emptyMap()
}
