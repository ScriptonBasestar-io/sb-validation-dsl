package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

inline fun <reified T> ValidationBuilderBase<*>.type(hint: String? = null) =
    addConstraint(
        hint ?: "must be of the correct type"
    ) { it is T }

inline fun <reified K, V> ValidationBuilderBase<out Map<K, V>>.hasKey(
    key: K,
    hint: String? = null
): Constraint<out Map<K, V>> =
    addConstraint(
        hint ?: "must hasKeys {0}".format(key.toString()),
    ) {
        it.containsKey(key)
    }

inline fun <reified K, reified V> ValidationBuilderBase<out Map<K, V>>.hasKeyValue(
    key: K,
    value: V,
    hint: String? = null
): Constraint<out Map<K, V>> =
    addConstraint(
        hint ?: "must hasKey {0} and value equals with {1}".format(key.toString(), value.toString()),
    ) {
        it[key] == value
    }

inline fun <reified K> ValidationBuilderBase<out Map<K, String>>.hasKeyValue(
    key: K,
    regex: Regex,
    hint: String? = null
): Constraint<out Map<K, String>> =
    addConstraint(
        hint ?: "must hasKey {0} and value matches with {1}".format(key.toString(), regex.toString()),
    ) {
        it[key] != null && regex.matches(it[key]!!)
    }

inline fun <reified K> ValidationBuilderBase<out Map<K, String>>.hasKeyValueNotBlank(
    key: K,
    hint: String? = null
): Constraint<out Map<K, String>> =
    addConstraint(
        hint ?: "must hasKey {0} and value not blank".format(key.toString()),
    ) {
        it[key] != null && it[key]!!.length > 1
    }
