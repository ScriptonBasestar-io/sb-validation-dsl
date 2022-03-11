package org.scriptonbasestar.validation.result

internal object NoValidationErrors : ValidationErrors, Map<String, List<ValidationError>> by emptyMap()
