package org.scriptonbasestar.validation.definition

import org.scriptonbasestar.validation.builder.ValidationBuilderBase

inline fun <reified T> ValidationBuilderBase<*>.type() =
    addConstraint(
        "must be of the correct type"
    ) { it is T }
