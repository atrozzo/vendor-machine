package com.vendor.machine.entity

import com.vendor.machine.model.Product
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "app_product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var id: Long = 0,
    @Column(name = "product_name")
    var productName: String,
    @Column(name = "product_cost")
    var cost: Int,
    @Column(name = "amount_available")
    var amount: Int
) {

    companion object ProductEntityMapper {
        fun toEntity(model: Product): ProductEntity {
            return ProductEntity(
                productName = model.productName,
                cost = model.cost,
                amount = model.amountAvailable
            )
        }
    }
}
