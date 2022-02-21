package com.vendor.machine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.vendor.machine.model.Deposit
import com.vendor.machine.model.Product
import com.vendor.machine.model.Purchase
import com.vendor.machine.model.User
import com.vendor.machine.model.UserAccount
import com.vendor.machine.service.ProductService
import com.vendor.machine.service.UserService
import com.vendor.machine.service.VendorMachineService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
@SpringBootTest
@AutoConfigureMockMvc
internal class VendorMachineIntTest {

    lateinit var mockmvc: MockMvc
    @Autowired
    lateinit var wac: WebApplicationContext

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var vendorMachineService: VendorMachineService

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    val purchase = Purchase(1L, 1L, 10, Product(5, "pepsi", 5))
    var userAccount: UserAccount = UserAccount(userId = 1L, currentAmount = 10)
    protected lateinit var deposit: Deposit

    @BeforeEach
    fun createStubs() {
        mockmvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    @Throws(Exception::class)
    fun `should call make a deposit`() {
        val newUser = User(username = "foo", password = "foo", role = "seller", amount = 0)
        val user = userService.createNewUser(newUser)
        deposit = Deposit(user.userid, 10)

        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.post("/api/vendormachine/actions/deposit/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deposit))
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call make a purchase`() {
        val newUser = User(username = "foo", password = "foo", role = "seller", amount = 0)
        val user = userService.createNewUser(newUser)
        val product = Product(productName = "cola", amountAvailable = 5, cost = 10)
        val newproduct = productService.addProduct(product)

        deposit = Deposit(user.userid, 10)
        vendorMachineService.deposit(deposit)
        val purchase = Purchase(userId = user.userid, productId = newproduct.productId, amount = 1, product = null)
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.post("/api/vendormachine/actions/purchase/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(purchase))
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }
}
