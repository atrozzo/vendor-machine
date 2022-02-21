package com.vendor.machine.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.vendor.machine.entity.UserEntity
import com.vendor.machine.validators.ValidCoinAMount
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull
data class Deposit @JsonCreator constructor(

    @Digits(integer = 15, fraction = 0, message = "not valid user id passed")
    @NotNull
    val userId: Long,

    @ValidCoinAMount
    val amount: Int
) {
    companion object DepositMapper {
        fun toModel(user: UserEntity): UserAccount {
            return UserAccount(
                userId = user.user_id,
                currentAmount = user.account?.amount ?: 0
            )
        }
    }
}
