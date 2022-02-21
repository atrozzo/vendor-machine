package com.vendor.machine.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.vendor.machine.service.exception.DepositException
import com.vendor.machine.service.exception.ProductException
import com.vendor.machine.service.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class VendorMachineExceptionHandler {

    @ExceptionHandler(ProductException::class)
    fun handleProductException(e: Exception): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                e.message?.let { ApiErrorResponse(status = HttpStatus.BAD_REQUEST, error = it) }
            )
    }

    @ExceptionHandler(UserException::class)
    fun handleUserException(e: Exception): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                e.message?.let { ApiErrorResponse(status = HttpStatus.BAD_REQUEST, error = it) }
            )
    }

    @ExceptionHandler(DepositException::class)
    fun handleDepositException(e: Exception): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                e.message?.let { ApiErrorResponse(status = HttpStatus.BAD_REQUEST, error = it) }
            )
    }
}
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiErrorResponse(val status: HttpStatus, val error: String)
