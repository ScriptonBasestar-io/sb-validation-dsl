package org.scriptonbasestar.validation.result

import org.scriptonbasestar.validation.error.ValidationErrors

data class ValidationResult(
    val errors: ValidationErrors,
)
