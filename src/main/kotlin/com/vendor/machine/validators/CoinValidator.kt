package com.vendor.machine.validators

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

class CoinValidator : ConstraintValidator<ValidCoinAMount, Int> {
    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        if (value?.mod(5) == 0) {
            return true
        }
        return false
    }
}

@Retention(AnnotationRetention.RUNTIME)
annotation class ValidCoinAMount(
    val message: String = "Coins amount format not valid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
