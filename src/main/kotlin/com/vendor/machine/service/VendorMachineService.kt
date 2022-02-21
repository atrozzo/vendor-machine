package com.vendor.machine.service

import com.vendor.machine.entity.ProductEntity
import com.vendor.machine.entity.UserEntity
import com.vendor.machine.model.Deposit
import com.vendor.machine.model.Product
import com.vendor.machine.model.Purchase
import com.vendor.machine.model.UserAccount
import com.vendor.machine.repository.ProductRepository
import com.vendor.machine.repository.UserRepository
import com.vendor.machine.service.exception.DepositException
import com.vendor.machine.service.exception.ProductException
import com.vendor.machine.service.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class VendorMachineService @Autowired constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun purchase(purchase: Purchase): Purchase {
        var user = userRepository.findById(purchase.userId).orElseThrow { UserException("User with $purchase.userId does not exist") }
        var product = productRepository.findById(purchase.productId)
            .orElseThrow { ProductException("This product with id ${purchase.productId} is not longer available   ") }

        updateUserAccount(purchase, product, user)
        updateInvetory(product, purchase)

        purchase.product = Product.toModel(product)
        return purchase
    }

    @Transactional
    fun deposit(deposit: Deposit): UserAccount {
        var user = userRepository.findById(deposit.userId).orElseThrow { UserException("User with $deposit.userId does not exist") }
        var sum = user.account?.amount ?: 0
        sum += deposit.amount
        if (user.account!!.amount >= 100)
            throw DepositException("Deposit limit exceeded")
        user.account!!.amount = sum

        return Deposit.toModel(userRepository.save(user))
    }

    fun cancelDeposit(userId: Long): Deposit {
        var user = userRepository.findById(userId).orElseThrow { UserException("User with $userId.userId does not exist") }

        user.account!!.amount = 0
        userRepository.save(user)
        return Deposit(userId, user.account!!.amount)
    }

    private fun validateAmountRequested(product: ProductEntity, purchase: Purchase): Int {
        if (product.amount < purchase.amount)
            throw ProductException("Not enough products available : ${product.amount}")
        return product.amount.minus(purchase.amount) // update inventory
    }
    private fun validateCost(purchase: Purchase, product: ProductEntity, user: UserEntity): Int {
        if (purchase.amount * product.cost > user.account!!.amount) {
            throw UserException("Not enough credit available for this purchase")
        }
        return product.cost.times(product.amount) // update user account
    }

    private fun updateInvetory(product: ProductEntity, purchase: Purchase) {
        product.amount = validateAmountRequested(product, purchase)
        if (product.amount == 0)
            productRepository.deleteById(product.id)
        else
            productRepository.save(product)
    }

    private fun updateUserAccount(purchase: Purchase, product: ProductEntity, user: UserEntity) {
        user.account!!.amount = validateCost(purchase, product, user)
        userRepository.save(user)
    }
}
