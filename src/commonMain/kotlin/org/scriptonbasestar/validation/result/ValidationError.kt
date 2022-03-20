package org.scriptonbasestar.validation.result

data class ValidationError(
    val dataPath: String,
    val message: String,
    val throwable: Throwable?,
) {
    override fun toString(): String {
        return "ValidationError(dataPath=$dataPath, message=$message)"
    }
}
