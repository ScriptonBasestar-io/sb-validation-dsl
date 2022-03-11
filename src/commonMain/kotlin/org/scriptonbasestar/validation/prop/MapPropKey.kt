package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.validation.MapValidation
import org.scriptonbasestar.validation.validation.NonNullPropertyValidation
import org.scriptonbasestar.validation.validation.OptionalPropertyValidation
import org.scriptonbasestar.validation.validation.RequiredPropertyValidation
import kotlin.reflect.KProperty1

private data class MapPropKey<T, K, V>(
    val property: KProperty1<T, Map<K, V>>,
    val modifier: PropModifier
) : PropKey<T>() {
    override fun build(builder: ValidationBuilderBase<*>): Validation<T> {
        @Suppress("UNCHECKED_CAST")
        val validations = (builder as ValidationBuilderBase<Map.Entry<K, V>>).build()
        return when (modifier) {
            PropModifier.NonNull -> NonNullPropertyValidation(property, MapValidation(validations))
            PropModifier.Optional -> OptionalPropertyValidation(property, MapValidation(validations))
            PropModifier.OptionalRequired -> RequiredPropertyValidation(property, MapValidation(validations))
        }
    }
}
