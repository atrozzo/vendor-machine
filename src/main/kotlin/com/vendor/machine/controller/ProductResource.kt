package com.vendor.machine.controller

import com.vendor.machine.model.Product
import com.vendor.machine.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["api/vendormachine"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductResource(
    private val service: ProductService
) {

    @Operation(summary = "Add a product to the Vendor Machine.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Product successfully created"),
            ApiResponse(responseCode = "500", description = "Unable to add new product")
        ]
    )
    @PostMapping(
        path = ["/products/"]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@NotNull @Valid @RequestBody product: Product): Product {
        return service.addProduct(product)
    }

    @PutMapping(
        path = ["/products/"]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateProduct(@NotNull @Valid @RequestBody product: Product): Product {
        return service.updateProduct(product)
    }

    @Operation(summary = "Delete an existing Product .")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            ApiResponse(responseCode = "400", description = "Product not found")
        ]
    )
    @DeleteMapping(
        path = ["/products/{productId}"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAUser(@PathVariable productId: Long) {
        service.removeProduct(productId)
    }

    @Operation(summary = "Get a Product by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Product found"),
            ApiResponse(responseCode = "400", description = "Product not found")
        ]
    )
    @GetMapping(
        path = ["/products/{productId}"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.OK)
    fun getAProduct(@PathVariable productId: Long): Product {
        return service.getProduct(productId)
    }

    @Operation(summary = "Get all available products .")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Products founds"),
            ApiResponse(responseCode = "400", description = "No product founds"),

        ]
    )
    @GetMapping(
        path = ["/products/"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.OK)
    fun getProducts(): List<Product> {
        return service.getProducts()
    }
}
