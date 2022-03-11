package org.scriptonbasestar.validation.prop

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.builder.ValidationBuilderBase

abstract class PropKey<T> {
    abstract fun build(builder: ValidationBuilderBase<*>): Validation<T>
}
