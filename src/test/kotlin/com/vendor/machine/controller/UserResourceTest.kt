package com.vendor.machine.controller

import com.vendor.machine.model.User
import com.vendor.machine.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class UserResourceTest {

    @Mock
    private lateinit var service: UserService

    @InjectMocks private lateinit var subject: UsersResource

    val newUser = User(1,"foo","foo","seller",0)
    val userToUpdate = User(1,"foo","foo","buyer",0)
    @Test
    fun `should add a user`() {
        whenever(service.createNewUser(newUser)).thenReturn(newUser)
        val observed = subject.createAUser(newUser)
        assertThat(observed).isSameAs(newUser)
    }

    @Test
    fun `should update a User`() {
        whenever(service.updateUser(newUser)).thenReturn(newUser)
        val observed = subject.updateAUser(newUser)
        assertThat(observed).isNotSameAs(userToUpdate)
    }

    @Test
    fun `should get a product`() {
        whenever(service.getUser(1L)).thenReturn(newUser)
        val observed = subject.getAUser(1L)
        assertThat(observed).isSameAs(newUser)
    }

    @Test
    fun `should get a list of user`() {
        whenever(service.getAllUsers()).thenReturn(listOf(userToUpdate))
        val observed = subject.getAllUsers()
        assertThat(observed.size).isEqualTo(1)
    }
}
