package com.vendor.machine.controller

import com.vendor.machine.model.Deposit
import com.vendor.machine.model.Product
import com.vendor.machine.model.Purchase
import com.vendor.machine.model.UserAccount
import com.vendor.machine.service.VendorMachineService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class VendorMachineTest {

    @Mock
    private lateinit var service: VendorMachineService

    @InjectMocks
    private lateinit var subject: VendorMachine

    @Test
    fun buyProduct() {
        val purchase = Purchase(1L, 1L, 10, Product(5, "pepsi", 5))
        whenever(service.purchase(purchase)).thenReturn(purchase)
        val observed = subject.buyProduct(purchase)
        Assertions.assertThat(observed).isSameAs(purchase)
    }

    @Test
    fun deposit() {
        var userAccount: UserAccount = UserAccount(userId = 1L, currentAmount = 10)
        whenever(service.deposit(Deposit(1L, 10))).thenReturn(userAccount)
        val observed = subject.deposit(Deposit(1L, 10))
        Assertions.assertThat(observed).isSameAs(userAccount)
    }
}
