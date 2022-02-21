package com.vendor.machine.model

import com.fasterxml.jackson.annotation.JsonCreator

class UserAccount @JsonCreator constructor(
    val userId: Long,
    val currentAmount: Int
)
