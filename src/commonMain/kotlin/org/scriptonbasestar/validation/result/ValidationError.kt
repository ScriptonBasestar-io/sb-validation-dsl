package org.scriptonbasestar.validation.result

interface ValidationError {
    val dataPath: String
    val message: String
}
