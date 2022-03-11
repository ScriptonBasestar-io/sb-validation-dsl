package org.scriptonbasestar.validation.result

internal data class PropertyValidationError(
    override val dataPath: String,
    override val message: String
) : ValidationError {
    override fun toString(): String {
        return "ValidationError(dataPath=$dataPath, message=$message)"
    }
}
