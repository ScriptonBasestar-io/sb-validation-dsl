package org.scriptonbasestar.validation.error

data class ValidationError(
    val path: String,
    val code: String,
    val value: Any?,
    val message: String,
)
