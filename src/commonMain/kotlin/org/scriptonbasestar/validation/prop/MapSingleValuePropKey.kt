package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.validation.NotNullMapKeyValuePropertyValidation
import org.scriptonbasestar.validation.validation.OptionalPropertyValidation
import org.scriptonbasestar.validation.validation.RequiredPropertyValidation
import kotlin.reflect.KProperty1

data class MapSingleValuePropKey<T, K, V>(
    val property: KProperty1<T, Map<K, V>>,
    val key: K,
    val modifier: PropModifier
) : PropKey<T>() {
    override fun build(builder: ValidationBuilderBase<*>): Validation<T> {
        @Suppress("UNCHECKED_CAST")
        val validations = (builder as ValidationBuilderBase<Map<K, V>>).build()
        return when (modifier) {
            PropModifier.NonNull -> NotNullMapKeyValuePropertyValidation(property, validations)
            PropModifier.Optional -> OptionalPropertyValidation(property, validations)
            PropModifier.OptionalRequired -> RequiredPropertyValidation(property, validations)
        }
    }
}
