package org.scriptonbasestar.validation

class Constraint<T> internal constructor(
    val hint: String,
    val test: (T) -> Boolean,
)
