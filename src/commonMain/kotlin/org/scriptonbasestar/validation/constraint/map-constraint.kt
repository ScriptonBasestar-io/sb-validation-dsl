package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

inline fun <reified T> ValidationBuilderBase<*>.type() =
    addConstraint(
        "must be of the correct type"
    ) { it is T }

inline fun <reified K, V> ValidationBuilderBase<out Map<K, V>>.hasKey(key: K): Constraint<out Map<K, V>> = addConstraint(
    "must hasKeys {0}".format(key.toString()),
) {
    when (it) {
        is Map<K, V> -> it.containsKey(key)
        else -> throw IllegalStateException("hasKey can not be applied to type ${K::class}")
    }
}

inline fun <reified K, reified V> ValidationBuilderBase<out Map<K, V>>.hasKeyValue(
    key: K,
    value: V,
): Constraint<out Map<K, V>> =
    addConstraint(
        "must hasKey {0} and value equals with {1}".format(key.toString(), value.toString()),
    ) {
        when (it) {
            is Map<K, V> -> it[key] == value
            else -> throw IllegalStateException("hasKeyEqualsValue can not be applied to type ${K::class}, ${V::class}")
        }
    }

inline fun <reified K> ValidationBuilderBase<out Map<K, String>>.hasKeyValue(
    key: K,
    regex: Regex,
): Constraint<out Map<K, String>> =
    addConstraint(
        "must hasKey {0} and value matches with {1}".format(key.toString(), regex.toString()),
    ) {
        println("========================")
        println(key)
        println(it[key])
        println(regex.matches(it[key]!!))
        when (it) {
            is Map<K, String> -> it[key] != null && regex.matches(it[key]!!)
            else -> throw IllegalStateException("hasKeyEqualsValue can not be applied to type ${K::class}")
        }
    }

inline fun <reified K> ValidationBuilderBase<out Map<K, String>>.hasKeyValueNotBlank(
    key: K,
): Constraint<out Map<K, String>> =
    addConstraint(
        "must hasKey {0} and value not blank".format(key.toString()),
    ) {
        when (it) {
            is Map<K, String> -> it[key] != null && it[key]!!.length > 1
            else -> throw IllegalStateException("hasKeyValueNotBlank can not be applied to type ${K::class}")
        }
    }
