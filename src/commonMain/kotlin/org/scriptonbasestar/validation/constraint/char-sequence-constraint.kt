package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

fun <T> ValidationBuilderBase<T>.enum(vararg allowed: T) =
    addConstraint(
        "must be one of: {0}".format(allowed.joinToString("', '", "'", "'")),
    ) { it in allowed }

inline fun <reified T : Enum<T>> ValidationBuilderBase<String>.enum(): Constraint<String> {
    val enumNames = enumValues<T>().map { it.name }
    return addConstraint(
        "must be one of: {0}".format(enumNames.joinToString("', '", "'", "'")),
    ) { it in enumNames }
}

fun ValidationBuilderBase<String>.minLength(length: Int): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("minLength requires the length to be >= 0") }
    return addConstraint(
        "must have at least {0} characters".format(length.toString()),
    ) { it.length >= length }
}

fun ValidationBuilderBase<String>.maxLength(length: Int): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("maxLength requires the length to be >= 0") }
    return addConstraint(
        "must have at most {0} characters".format(length.toString()),
    ) { it.length <= length }
}

fun ValidationBuilderBase<String>.pattern(pattern: String) = pattern(pattern.toRegex())

fun ValidationBuilderBase<String>.pattern(pattern: Regex) = addConstraint(
    "must match the expected pattern",
) { it.matches(pattern) }
