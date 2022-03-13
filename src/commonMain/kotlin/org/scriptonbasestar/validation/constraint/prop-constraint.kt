package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

fun <T> ValidationBuilderBase<T>.const(expected: T) =
    addConstraint(
        "must be {0}".format(expected?.let { "'$it'" } ?: "null"),
    ) { expected == it }

inline fun <reified T> ValidationBuilderBase<T>.uniqueItems(unique: Boolean): Constraint<T> = addConstraint(
    "all items must be unique"
) {
    !unique || when (it) {
        is Iterable<*> -> it.distinct().count() == it.count()
        is Array<*> -> it.distinct().count() == it.count()
        else -> throw IllegalStateException("uniqueItems can not be applied to type ${T::class}")
    }
}
