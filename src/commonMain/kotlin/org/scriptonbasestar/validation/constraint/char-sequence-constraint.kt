package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format

fun <T> ValidationBuilderBase<T>.enum(vararg allowed: T, hint: String? = null) =
    addConstraint(
        hint ?: "must be one of: {0}".format(allowed.joinToString("', '", "'", "'")),
    ) { it in allowed }

inline fun <reified T : Enum<T>> ValidationBuilderBase<String>.enum(hint: String? = null): Constraint<String> {
    val enumNames = enumValues<T>().map { it.name }
    return addConstraint(
        hint ?: "must be one of: {0}".format(enumNames.joinToString("', '", "'", "'")),
    ) { it in enumNames }
}

fun ValidationBuilderBase<String?>.notBlank(hint: String? = null) =
    addConstraint(
        hint ?: "must not null or empty",
    ) { it != null && it.length > 1 }

fun ValidationBuilderBase<String>.minLength(length: Int, hint: String? = null): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("minLength requires the length to be >= 0") }
    return addConstraint(
        hint ?: "must have at least {0} characters".format(length.toString()),
    ) { it.length >= length }
}

fun ValidationBuilderBase<String>.maxLength(length: Int, hint: String? = null): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("maxLength requires the length to be >= 0") }
    return addConstraint(
        hint ?: "must have at most {0} characters".format(length.toString()),
    ) { it.length <= length }
}

fun ValidationBuilderBase<String>.startsWith(value: String, ignoreCase: Boolean = true, hint: String? = null) = addConstraint(
    hint ?: "must match the expected pattern",
) { it.startsWith(value, ignoreCase = ignoreCase) }

fun ValidationBuilderBase<String>.pattern(pattern: String, hint: String? = null) = pattern(pattern.toRegex(), hint)

fun ValidationBuilderBase<String>.pattern(pattern: Regex, hint: String? = null) = addConstraint(
    hint ?: "must match the expected pattern",
) { it.matches(pattern) }
