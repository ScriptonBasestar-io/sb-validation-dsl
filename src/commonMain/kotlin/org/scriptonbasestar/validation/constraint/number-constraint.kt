package org.scriptonbasestar.validation.constraint

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.builder.ValidationBuilderBase
import org.scriptonbasestar.validation.util.format
import kotlin.math.roundToInt

fun <T : Number> ValidationBuilderBase<T>.multipleOf(factor: Number): Constraint<T> {
    val factorAsDouble = factor.toDouble()
    require(factorAsDouble > 0) { "multipleOf requires the factor to be strictly larger than 0" }
    return addConstraint(
        "must be a multiple of '{0}'".format(factor.toString()),
    ) {
        val division = it.toDouble() / factorAsDouble
        division.compareTo(division.roundToInt()) == 0
    }
}

fun <T : Number> ValidationBuilderBase<T>.maximum(maximumInclusive: Number) = addConstraint(
    "must be at most '{0}'".format(maximumInclusive.toString()),
) { it.toDouble() <= maximumInclusive.toDouble() }

fun <T : Number> ValidationBuilderBase<T>.exclusiveMaximum(maximumExclusive: Number) = addConstraint(
    "must be less than '{0}'".format(maximumExclusive.toString()),
) { it.toDouble() < maximumExclusive.toDouble() }

fun <T : Number> ValidationBuilderBase<T>.minimum(minimumInclusive: Number) = addConstraint(
    "must be at least '{0}'".format(minimumInclusive.toString()),
) { it.toDouble() >= minimumInclusive.toDouble() }

fun <T : Number> ValidationBuilderBase<T>.exclusiveMinimum(minimumExclusive: Number) = addConstraint(
    "must be greater than '{0}'".format(minimumExclusive.toString()),
) { it.toDouble() > minimumExclusive.toDouble() }
