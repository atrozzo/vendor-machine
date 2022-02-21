package com.vendor.machine.repository

import com.vendor.machine.entity.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductRepository : CrudRepository<ProductEntity, Long> {

    fun findByProductName(productName: String): Optional<ProductEntity>
}
