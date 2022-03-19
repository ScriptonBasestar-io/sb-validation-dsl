package org.scriptonbasestar.validation

class Constraint<T> internal constructor(
    var hint: String,
    val test: (T) -> Boolean,
//    var throwable: Throwable? = null,
) {
    infix fun hint(hint: String) {
        this.hint = hint
    }

//    infix fun ex(throwable: Throwable) {
//        this.throwable = throwable
//    }
}
