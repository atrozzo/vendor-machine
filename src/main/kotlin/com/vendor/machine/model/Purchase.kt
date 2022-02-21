package com.vendor.machine.model

import com.fasterxml.jackson.annotation.JsonCreator
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class Purchase @JsonCreator constructor(

    @Digits(integer = 15, fraction = 0, message = "not valid user id passed")
    @NotNull
    val userId: Long,
    @Digits(integer = 15, fraction = 0, message = "not valid product id passed")
    @NotNull
    val productId: Long,

    val amount: Int,
    var product: Product?,
)
