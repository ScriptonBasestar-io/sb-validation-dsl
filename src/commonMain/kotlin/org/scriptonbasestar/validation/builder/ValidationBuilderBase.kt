package org.scriptonbasestar.validation.builder

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.prop.PropKey
import org.scriptonbasestar.validation.validation.ValidationNode
import kotlin.jvm.JvmName
import kotlin.reflect.KProperty1

abstract class ValidationBuilderBase<T> {
    protected val constraints = mutableListOf<Constraint<T>>()
    protected val subValidations = mutableMapOf<PropKey<T>, ValidationBuilderBase<*>>()
    protected val prebuiltValidations = mutableListOf<Validation<T>>()

    fun addConstraint(
        hint: String,
        test: (T) -> Boolean,
    ): Constraint<T> =
        Constraint(hint, test).also(constraints::add)

    fun build(): Validation<T> {
        val nestedValidations = subValidations.map { (key, builder) ->
            key.build(builder)
        }
        return ValidationNode(constraints, nestedValidations + prebuiltValidations)
    }

    abstract operator fun <R> KProperty1<T, R>.invoke(init: ValidationBuilderBase<R>.() -> Unit)

    internal abstract fun <R> onEachIterable(
        prop: KProperty1<T, Iterable<R>>,
        init: ValidationBuilderBase<R>.() -> Unit
    )

    @JvmName("onEachIterable")
    infix fun <R> KProperty1<T, Iterable<R>>.onEach(init: ValidationBuilderBase<R>.() -> Unit) =
        onEachIterable(this, init)

    internal abstract fun <R> onEachArray(prop: KProperty1<T, Array<R>>, init: ValidationBuilderBase<R>.() -> Unit)

    @JvmName("onEachArray")
    infix fun <R> KProperty1<T, Array<R>>.onEach(init: ValidationBuilderBase<R>.() -> Unit) = onEachArray(this, init)

    internal abstract fun <K, V> onHashMap(
        prop: KProperty1<T, Map<K, V>>,
        init: ValidationBuilderBase<Map.Entry<K, V>>.() -> Unit
    )

    @JvmName("onHashMap")
    infix fun <K, V> KProperty1<T, Map<K, V>>.onHash(init: ValidationBuilderBase<Map.Entry<K, V>>.() -> Unit) =
        onHashMap(this, init)

    internal abstract fun <K, V> KProperty1<T, Map<K, V>>.onKeyExists(key: K, init: ValidationBuilderBase<V>.() -> Unit)

//    internal abstract fun <R> KProperty1<R?>.ifValuePresent(init: ValidationBuilderBase<R>.() -> Unit)

//    abstract infix fun <R> R?.ifKeyPresent(init: ValidationBuilderBase<R>.() -> Unit)

    abstract infix fun <R> KProperty1<T, R?>.ifPresent(init: ValidationBuilderBase<R>.() -> Unit)
    abstract infix fun <R> KProperty1<T, R?>.required(init: ValidationBuilderBase<R>.() -> Unit)
    abstract fun run(validation: Validation<T>)
    abstract val <R> KProperty1<T, R>.has: ValidationBuilderBase<R>
    abstract infix fun Constraint<T>.hint(hint: String): Constraint<T>
}
