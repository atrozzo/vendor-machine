package com.vendor.machine.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "app_role")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    var role_id: Long,

    @Column(name = "role_name", updatable = false)
    val roleName: String? = null,

    @Column(name = "description", updatable = false)
    var description: String? = null,

    @ManyToMany(mappedBy = "roleEntities")
    var userEntities: Set<UserEntity>? = null
)
