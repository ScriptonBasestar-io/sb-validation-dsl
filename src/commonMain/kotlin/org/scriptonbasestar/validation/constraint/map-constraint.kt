package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

inline fun <reified T> ValidationBuilderBase<*>.type() =
    addConstraint(
        "must be of the correct type"
    ) { it is T }

inline fun <reified K, V> ValidationBuilderBase<Map<K, V>>.hasKey(key: K): Constraint<Map<K, V>> = addConstraint(
    "must hasKeys {0}".format(key.toString()),
) {
    when (it) {
        is Map<K, V> -> it.containsKey(key)
        else -> throw IllegalStateException("hasKey can not be applied to type ${K::class}")
    }
}

inline fun <reified K, reified V> ValidationBuilderBase<Map<K, V>>.hasKeyValue(
    key: K,
    value: V,
): Constraint<Map<K, V>> =
    addConstraint(
        "must hasKey {0} and value equals with {1}".format(key.toString(), value.toString()),
    ) {
        when (it) {
            is Map<K, V> -> it[key] == value
            else -> throw IllegalStateException("hasKeyEqualsValue can not be applied to type ${K::class}, ${V::class}")
        }
    }

inline fun <reified K> ValidationBuilderBase<Map<K, String>>.hasKeyValue(
    key: K,
    regex: Regex,
): Constraint<Map<K, String>> =
    addConstraint(
        "must hasKey {0} and value equals with {1}".format(key.toString(), regex.toString()),
    ) {
        when (it) {
            is Map<K, String> -> it[key] != null && it[key]!!.matches(regex)
            else -> throw IllegalStateException("hasKeyEqualsValue can not be applied to type ${K::class}")
        }
    }

inline fun <reified K> ValidationBuilderBase<Map<K, String>>.hasKeyValueNotBlank(
    key: K,
): Constraint<Map<K, String>> =
    addConstraint(
        "must hasKey {0} and value not blank".format(key.toString()),
    ) {
        when (it) {
            is Map<K, String> -> it[key] != null && it[key]!!.length > 1
            else -> throw IllegalStateException("hasKeyValueNotBlank can not be applied to type ${K::class}")
        }
    }
