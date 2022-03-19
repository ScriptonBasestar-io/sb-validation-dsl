package org.scriptonbasestar.validation

class Constraint<T> internal constructor(
    var hint: String,
    val test: (T) -> Boolean,
) {
    infix fun hint(hint: String) {
        this.hint = hint
    }
}
