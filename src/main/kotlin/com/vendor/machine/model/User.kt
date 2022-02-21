package com.vendor.machine.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.vendor.machine.entity.UserEntity
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class User @JsonCreator constructor(

    var userid: Long = 0,

    @Schema(description = "This is the user name used to opertate the vendor machine")
    @NotNull(message = "Username cannot be null")
    @Size(min = 8, max = 20, message = "Username cannot be less then 8 and more then 20 chars")
    val username: String,

    @Schema(description = "This is the user password  the vendor machine")
    @NotNull
    @Min(message = "Password has to be at least 8 characters", value = 8)
    val password: String,

    @Schema(description = "This si the role a user can have")
    @NotNull
    var role: String,

    var amount: Int

) {
    companion object UserMapper {
        fun toModel(user: UserEntity): User {
            return User(
                username = user.username,
                password = user.password,
                role = user.role,
                userid = user.user_id,
                amount = user.account?.amount ?: 0
            )
        }
    }
}
