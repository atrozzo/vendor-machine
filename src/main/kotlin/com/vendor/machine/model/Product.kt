package com.vendor.machine.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.vendor.machine.entity.ProductEntity
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

data class Product @JsonCreator constructor(
    @Schema(description = "This is the product cost expressed in coins and can only accept  5, 10, 20, 50 and 100 values")
    @NotNull(message = "Cost cannot be null")
    var cost: Int,
    @Schema(description = "This is the product cost expressed in coins and can only accept  5, 10, 20, 50 and 100 values")
    @NotNull(message = "Cost cannot be null")
    val productName: String,
    @Schema(description = "Amount fo this product still available")
    val amountAvailable: Int,
    val productId: Long = 0
) {
    companion object ProductMapper {
        fun toModel(product: ProductEntity): Product {
            return Product(
                productName = product.productName,
                cost = product.cost,
                amountAvailable = product.amount,
                productId = product.id
            )
        }
    }
}
