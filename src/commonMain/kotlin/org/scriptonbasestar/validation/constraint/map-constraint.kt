package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

inline fun <reified T> ValidationBuilderBase<*>.type() =
    addConstraint(
        "must be of the correct type"
    ) { it is T }

inline fun <reified T> ValidationBuilderBase<T>.hasKey(key: String): Constraint<T> = addConstraint(
    "must hasKey {0}".format(key),
) {
    when (it) {
        is Map<*, *> -> it.containsKey(key)
        else -> throw IllegalStateException("hasKey can not be applied to type ${T::class}")
    }
}

inline fun <reified T> ValidationBuilderBase<T>.hasKeyEqualsValue(key: String, value: String): Constraint<T> =
    addConstraint(
        "must hasKey {0} and value equals with {1}".format(key, value),
    ) {
        when (it) {
            is Map<*, *> -> it[key] == value
            else -> throw IllegalStateException("hasKeyEqualsValue can not be applied to type ${T::class}")
        }
    }
