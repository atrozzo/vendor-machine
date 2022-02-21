package com.vendor.machine.aspects

import com.vendor.machine.model.Purchase
import com.vendor.machine.service.ProductService
import com.vendor.machine.service.UserService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Aspect
class PurchaseValidator @Autowired constructor(
    private val userService: UserService,
    private val productService: ProductService
) {
/*
    @Before("execution(* com.vendor.machine.service.VendorMachineService.purchase(...)) && args()")
    fun validatePuchase(jointPoint: JoinPoint, purchase: Purchase) {
        userService.getUser(purchase.userId)
        productService.getProduct(purchase.productId)
        log.info("payload is valid : ${purchase.toString()}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(PurchaseValidator::class.java)
    }*/
}
