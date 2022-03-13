package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.validation.NonNullPropertyValidation
import org.scriptonbasestar.validation.validation.OptionalPropertyValidation
import org.scriptonbasestar.validation.validation.RequiredPropertyValidation
import kotlin.reflect.KProperty1

data class SingleValuePropKey<T, R>(
    val property: KProperty1<T, R>,
    val modifier: PropModifier
) : PropKey<T>() {
    override fun build(builder: ValidationBuilderBase<*>): Validation<T> {
        @Suppress("UNCHECKED_CAST")
        val validations = (builder as ValidationBuilderBase<R>).build()
        return when (modifier) {
            PropModifier.NonNull -> NonNullPropertyValidation(property, validations)
            PropModifier.Optional -> OptionalPropertyValidation(property, validations)
            PropModifier.OptionalRequired -> RequiredPropertyValidation(property, validations)
        }
    }
}
