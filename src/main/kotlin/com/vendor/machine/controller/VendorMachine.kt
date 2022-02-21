package com.vendor.machine.controller

import com.vendor.machine.model.Deposit
import com.vendor.machine.model.Purchase
import com.vendor.machine.model.UserAccount
import com.vendor.machine.service.VendorMachineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/api/vendormachine"], produces = [MediaType.APPLICATION_JSON_VALUE])

class VendorMachine(
    private val service: VendorMachineService
) {

    @Operation(summary = "Execute a purchase action for a specific product")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Product purchased"),
            ApiResponse(responseCode = "400", description = "Purchase options are not valid")
        ]
    )
    @PostMapping(
        path = ["/actions/purchase"]
    )
    @ResponseStatus(HttpStatus.OK)
    fun buyProduct(@NotNull @Valid @RequestBody purchase: Purchase): Purchase {
        return service.purchase(purchase)
    }

    @PostMapping(
        path = ["/actions/deposit"]
    )
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@NotNull @Valid @RequestBody deposit: Deposit): UserAccount {
        return service.deposit(deposit)
    }
}
