package com.vendor.machine.service

import com.vendor.machine.entity.UserEntity
import com.vendor.machine.model.User
import com.vendor.machine.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

    @Mock
    private lateinit var repository: UserRepository

    @InjectMocks
    private lateinit var subject: UserService

    val newUser = User(1, "foo", "foo", "seller", 0)
    val userEntity: UserEntity = UserEntity(username = "foo", password = "foo", role = "seller", user_id = 0, account = null, roleEntities = null)

    @Test
    fun createNewUser() {
        whenever(repository.save(userEntity)).thenReturn(userEntity)
        val observed = subject.createNewUser(newUser)
        assertThat(observed.userid).isSameAs(userEntity.user_id)
    }

    @Test
    fun updateUser() {
    }

    @Test
    fun deleteUser() {
    }

    @Test
    fun getUser() {
    }

    @Test
    fun getAllUsers() {
    }
}
