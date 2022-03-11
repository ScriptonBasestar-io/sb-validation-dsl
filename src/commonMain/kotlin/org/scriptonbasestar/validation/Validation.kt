package org.scriptonbasestar.validation

import org.scriptonbasestar.validation.builder.ValidationRootBuilder
import org.scriptonbasestar.validation.result.ValidationResult

@ValidationDsl
interface Validation<T> {
    companion object {
        operator fun <T> invoke(init: ValidationRootBuilder<T>.() -> Unit): Validation<T> =
            ValidationRootBuilder<T>().apply(init).build()
//        operator fun <T> invoke(init: ValidationCollectionBuilder<T>.() -> Unit): Validation<T> =
//            ValidationCollectionBuilder<T>().apply(init).build()
//        operator fun <T> invoke(init: ValidationMapBuilder<T>.() -> Unit): Validation<T> =
//            ValidationMapBuilder<T>().apply(init).build()
    }

    fun validate(value: T): ValidationResult<T>
    operator fun invoke(value: T) = validate(value)
}
