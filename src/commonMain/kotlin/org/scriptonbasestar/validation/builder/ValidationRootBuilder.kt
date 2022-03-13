package org.scriptonbasestar.validation.builder

import org.scriptonbasestar.validation.Constraint
import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.prop.*
import kotlin.reflect.KProperty1

class ValidationRootBuilder<T> : ValidationBuilderBase<T>() {

    private fun <R> KProperty1<T, R?>.getOrCreateBuilder(modifier: PropModifier): ValidationBuilderBase<R> {
        val key = SingleValuePropKey(this, modifier)
        @Suppress("UNCHECKED_CAST")
        return (subValidations.getOrPut(key) { ValidationRootBuilder<R>() } as ValidationBuilderBase<R>)
    }

    private fun <R> KProperty1<T, Iterable<R>>.getOrCreateIterablePropertyBuilder(modifier: PropModifier): ValidationBuilderBase<R> {
        val key = IterablePropKey(this, modifier)
        @Suppress("UNCHECKED_CAST")
        return (subValidations.getOrPut(key) { ValidationRootBuilder<R>() } as ValidationBuilderBase<R>)
    }

    private fun <R> PropKey<T>.getOrCreateBuilder(): ValidationBuilderBase<R> {
        @Suppress("UNCHECKED_CAST")
        return (subValidations.getOrPut(this) { ValidationRootBuilder<R>() } as ValidationBuilderBase<R>)
    }

    override fun <R> KProperty1<T, R>.invoke(init: ValidationBuilderBase<R>.() -> Unit) {
        getOrCreateBuilder(PropModifier.NonNull).also(init)
    }

    override fun <R> onEachIterable(prop: KProperty1<T, Iterable<R>>, init: ValidationBuilderBase<R>.() -> Unit) {
        prop.getOrCreateIterablePropertyBuilder(PropModifier.NonNull).also(init)
    }

    override fun <R> onEachArray(prop: KProperty1<T, Array<R>>, init: ValidationBuilderBase<R>.() -> Unit) {
        ArrayPropKey(prop, PropModifier.NonNull).getOrCreateBuilder<R>().also(init)
    }

    override fun <K, V> onHashMap(
        prop: KProperty1<T, Map<K, V>>,
        init: ValidationBuilderBase<Map.Entry<K, V>>.() -> Unit
    ) {
        MapPropKey(prop, PropModifier.NonNull).getOrCreateBuilder<Map.Entry<K, V>>().also(init)
    }

    override fun <K, V> KProperty1<T, Map<K, V>>.onKeyExists(
        key: K,
        init: ValidationBuilderBase<V>.() -> Unit
    ) {
        MapKeyValuePropKey(this, key, PropModifier.NonNull).getOrCreateBuilder<Map.Entry<K, V>>()
    }

//    override fun <R> KProperty1<R?>.ifValuePresent(init: ValidationBuilderBase<R>.() -> Unit) {
//        getOrCreateBuilder(PropModifier.Optional).also(init)
//    }
//
//    override fun <R> R?.ifKeyPresent(init: ValidationBuilderBase<R>.() -> Unit) {
//        return OptionalPropertyValidation(property, validations)
//    }

    override fun <R> KProperty1<T, R?>.ifPresent(init: ValidationBuilderBase<R>.() -> Unit) {
        getOrCreateBuilder(PropModifier.Optional).also(init)
    }

    override fun <R> KProperty1<T, R?>.required(init: ValidationBuilderBase<R>.() -> Unit) {
        getOrCreateBuilder(PropModifier.OptionalRequired).also(init)
    }

    override fun run(validation: Validation<T>) {
        prebuiltValidations.add(validation)
    }

    override val <R> KProperty1<T, R>.has: ValidationBuilderBase<R>
        get() = getOrCreateBuilder(PropModifier.NonNull)

    override fun Constraint<T>.hint(hint: String): Constraint<T> =
        Constraint(hint, this.test).also { constraints.remove(this); constraints.add(it) }
}
