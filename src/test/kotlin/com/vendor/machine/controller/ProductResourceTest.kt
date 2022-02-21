package com.vendor.machine.controller

import com.vendor.machine.model.Product
import com.vendor.machine.service.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class ProductResourceTest {

    @Mock
    private lateinit var service: ProductService

    @InjectMocks private lateinit var subject: ProductResource

    val productToAdd = Product(5, "pepsi", 5)
    val productToupdate = Product(5, "pepsi", 10)

    @Test
    fun `should add a product`() {
        whenever(service.addProduct(productToAdd)).thenReturn(productToAdd)
        val observed = subject.addProduct(productToAdd)
        assertThat(observed).isSameAs(productToAdd)
    }

    @Test
    fun `should update a product`() {
        whenever(service.updateProduct(productToupdate)).thenReturn(productToupdate)
        val observed = subject.updateProduct(productToupdate)
        assertThat(observed).isNotSameAs(productToAdd)
    }

    @Test
    fun `should get a product`() {
        whenever(service.getProduct(1L)).thenReturn(productToAdd)
        val observed = subject.getAProduct(1L)
        assertThat(observed).isSameAs(productToAdd)
    }

    @Test
    fun `should get a list of product`() {
        whenever(service.getProducts()).thenReturn(listOf(productToAdd))
        val observed = subject.getProducts()
        assertThat(observed.size).isEqualTo(1)
    }
}
