package org.scriptonbasestar.validation.result

internal class DefaultValidationErrors(private val errors: Map<String, List<ValidationError>>) :
    ValidationErrors,
    Map<String, List<ValidationError>> by errors {
    override fun toString(): String {
        return errors.toString()
    }
}
