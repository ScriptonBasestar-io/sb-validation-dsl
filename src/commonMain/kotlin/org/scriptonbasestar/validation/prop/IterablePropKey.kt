package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.validation.IterableValidation
import org.scriptonbasestar.validation.validation.NonNullPropertyValidation
import org.scriptonbasestar.validation.validation.OptionalPropertyValidation
import org.scriptonbasestar.validation.validation.RequiredPropertyValidation
import kotlin.reflect.KProperty1

private data class IterablePropKey<T, R>(
    val property: KProperty1<T, Iterable<R>>,
    val modifier: PropModifier
) : PropKey<T>() {
    override fun build(builder: ValidationBuilderBase<*>): Validation<T> {
        @Suppress("UNCHECKED_CAST")
        val validations = (builder as ValidationBuilderBase<R>).build()
        return when (modifier) {
            PropModifier.NonNull -> NonNullPropertyValidation(property, IterableValidation(validations))
            PropModifier.Optional -> OptionalPropertyValidation(property, IterableValidation(validations))
            PropModifier.OptionalRequired -> RequiredPropertyValidation(property, IterableValidation(validations))
        }
    }
}
