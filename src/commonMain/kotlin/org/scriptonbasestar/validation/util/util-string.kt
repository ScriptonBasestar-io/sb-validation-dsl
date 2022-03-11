package org.scriptonbasestar.validation.util

fun String.format(vararg args: String): String {
    for (arg in args) {
        this.replaceFirst("\\{[0-9]\\}".toRegex(), arg)
    }
    return this
}
