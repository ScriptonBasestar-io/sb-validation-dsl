package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.validation.ArrayValidation
import org.scriptonbasestar.validation.validation.NonNullPropertyValidation
import org.scriptonbasestar.validation.validation.OptionalPropertyValidation
import org.scriptonbasestar.validation.validation.RequiredPropertyValidation
import kotlin.reflect.KProperty1

private data class ArrayPropKey<T, R>(
    val property: KProperty1<T, Array<R>>,
    val modifier: PropModifier
) : PropKey<T>() {
    override fun build(builder: ValidationBuilderBase<*>): Validation<T> {
        @Suppress("UNCHECKED_CAST")
        val validations = (builder as ValidationBuilderBase<R>).build()
        return when (modifier) {
            PropModifier.NonNull -> NonNullPropertyValidation(property, ArrayValidation(validations))
            PropModifier.Optional -> OptionalPropertyValidation(property, ArrayValidation(validations))
            PropModifier.OptionalRequired -> RequiredPropertyValidation(property, ArrayValidation(validations))
        }
    }
}
