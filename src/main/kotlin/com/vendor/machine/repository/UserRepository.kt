package com.vendor.machine.repository

import com.vendor.machine.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): Optional<UserEntity>

    fun deleteByUsername(username: String)
}
