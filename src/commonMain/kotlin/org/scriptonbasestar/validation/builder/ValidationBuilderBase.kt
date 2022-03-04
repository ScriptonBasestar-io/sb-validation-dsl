package org.scriptonbasestar.validation.builder

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.Validation

abstract class ValidationBuilderBase<T> {
    protected val constraints = mutableListOf<Constraint<T>>()
    protected val prebuiltValidations = mutableListOf<Validation<T>>()

    fun addConstraint(
        hint: String,
        test: (T) -> Boolean,
    ): Constraint<T> =
        Constraint(hint, test).also(constraints::add)

    abstract fun build(): Validation<T>
}
