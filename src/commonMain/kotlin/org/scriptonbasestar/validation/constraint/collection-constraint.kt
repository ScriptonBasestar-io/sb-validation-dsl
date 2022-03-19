package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

inline fun <reified T> ValidationBuilderBase<T>.minItems(minSize: Int, hint: String? = null): Constraint<T> =
    addConstraint(
        hint ?: "must have at least {0} items".format(minSize.toString()),
    ) {
        when (it) {
            is Iterable<*> -> it.count() >= minSize
            is Array<*> -> it.count() >= minSize
            is Map<*, *> -> it.count() >= minSize
            else -> throw IllegalStateException("minItems can not be applied to type ${T::class}")
        }
    }

inline fun <reified T> ValidationBuilderBase<T>.maxItems(maxSize: Int, hint: String? = null): Constraint<T> =
    addConstraint(
        hint ?: "must have at most {0} items".format(maxSize.toString()),
    ) {
        when (it) {
            is Iterable<*> -> it.count() <= maxSize
            is Array<*> -> it.count() <= maxSize
            is Map<*, *> -> it.count() <= maxSize
            else -> throw IllegalStateException("maxItems can not be applied to type ${T::class}")
        }
    }

inline fun <reified T : Map<*, *>> ValidationBuilderBase<T>.minProperties(
    minSize: Int,
    hint: String? = null
): Constraint<T> =
    minItems(minSize, hint ?: "must have at least {0} properties".format(minSize.toString()))

inline fun <reified T : Map<*, *>> ValidationBuilderBase<T>.maxProperties(
    maxSize: Int,
    hint: String? = null
): Constraint<T> =
    maxItems(maxSize, hint ?: "must have at most {0} properties".format(maxSize.toString()))
