package com.vendor.machine.service

import com.vendor.machine.entity.ProductEntity
import com.vendor.machine.model.Product
import com.vendor.machine.repository.ProductRepository
import com.vendor.machine.service.exception.ProductException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(
    private val repository: ProductRepository,
) {

    fun addProduct(product: Product): Product {
        if (repository.findByProductName(productName = product.productName).isEmpty)
            return Product.toModel(repository.save(ProductEntity.toEntity(product)))
        else
            throw ProductException("A Product with the same name already exist: ${product.productName}")
    }

    fun updateProduct(product: Product): Product {
        var entityProduct = repository.findById(product.productId).orElseThrow { ProductException("Product not found ${product.productId}") }
        entityProduct.amount = product.amountAvailable
        entityProduct.cost = product.cost
        entityProduct.productName = product.productName

        return Product.toModel(repository.save(entityProduct))
    }

    fun removeProduct(productId: Long) {
        repository.deleteById(productId)
    }

    fun getProduct(productId: Long): Product {
        val product = repository.findById(productId)
        return Product.toModel(
            product.orElseThrow {
                ProductException("Product with id: {$productId } not found")
            }
        )
    }

    fun getProducts(): List<Product> {
        return repository.findAll().map { product -> Product.toModel(product) }
    }
}
