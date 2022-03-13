package org.scriptonbasestar.validation.util

fun String.format(vararg args: String): String {
    return args.foldIndexed(this) { idx, hint, values ->
        hint.replace("{$idx}", values)
    }
}
