package com.vendor.machine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.vendor.machine.model.Product
import com.vendor.machine.service.ProductService
import com.vendor.machine.service.exception.ProductException
import org.assertj.core.api.Assertions
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
internal class ProductResourceIntTest {

    lateinit var mockmvc: MockMvc
    @Autowired
    lateinit var wac: WebApplicationContext

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    protected lateinit var product: Product

    @BeforeEach
    fun createStubs() {
        product = Product(productName = "cola", amountAvailable = 5, cost = 10)
        mockmvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    @Throws(Exception::class)
    fun `should call get all product`() {
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.get("/api/vendormachine/products/")
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call get a Product`() {
        val result = productService.addProduct(product)
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.get("/api/vendormachine/products/${result.productId}")
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call  for product creation `() {
        val result = objectMapper.writeValueAsString(product)
        val ok = MockMvcResultMatchers.status()
            .isCreated
        val builder = MockMvcRequestBuilders.post("/api/vendormachine/products/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(result)
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call for product update `() {
        val newproduct = productService.addProduct(product)
        newproduct.cost = 5
        val productJsonFormat = objectMapper.writeValueAsString(newproduct)
        val ok = MockMvcResultMatchers.status()
            .isAccepted
        val builder = MockMvcRequestBuilders.put("/api/vendormachine/products/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(productJsonFormat)
        val value = this.mockmvc.perform(builder)
            .andExpect(ok).andReturn()
        val returned = objectMapper.readValue(value.response.contentAsString, Product::class.java)
        Assertions.assertThat(returned.cost).isEqualTo(5)
    }

    @Test
    @Throws(Exception::class)
    fun `should delete a user`() {

        val newproduct = productService.addProduct(product)

        val contentNewUSer = objectMapper.writeValueAsString(newproduct)
        val ok = MockMvcResultMatchers.status()
            .isNoContent
        val builder = MockMvcRequestBuilders.delete("/api/vendormachine/products/${newproduct.productId}")
            .contentType(APPLICATION_JSON_VALUE)
            .content(contentNewUSer)
        this.mockmvc.perform(builder)
            .andExpect(ok)
        try {
            productService.getProduct(newproduct.productId)
        } catch (e: ProductException) {
            Assertions.assertThat((e.message.equals("Product with id: {$newproduct.productId} not found")))
        }
    }
}
