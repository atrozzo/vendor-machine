package com.vendor.machine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.vendor.machine.model.User
import com.vendor.machine.service.UserService
import com.vendor.machine.service.exception.UserException
import org.assertj.core.api.Assertions.assertThat
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
internal class UserResourceIntTest {

    lateinit var mockmvc: MockMvc
    @Autowired
    lateinit var wac: WebApplicationContext

    @Autowired
    lateinit var userService: UserService

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun createStubs() {
        mockmvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    @Throws(Exception::class)
    fun `should call get all users`() {
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.get("/api/vendormachine/users/")
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call get a user`() {
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.get("/api/vendormachine/users/1")
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }
    val newUser = User(username = "foo", password = "foo", role = "seller", amount = 0)

    @Test
    @Throws(Exception::class)
    fun `should call  for user creation `() {
        val contentNewUSer = objectMapper.writeValueAsString(newUser)
        val ok = MockMvcResultMatchers.status()
            .isCreated
        val builder = MockMvcRequestBuilders.post("/api/vendormachine/users/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(contentNewUSer)
        this.mockmvc.perform(builder)
            .andExpect(ok)
    }

    @Test
    @Throws(Exception::class)
    fun `should call  for user update `() {

        val user = userService.createNewUser(newUser)

        user.role = "buyer"

        val contentNewUSer = objectMapper.writeValueAsString(user)
        val ok = MockMvcResultMatchers.status()
            .isOk
        val builder = MockMvcRequestBuilders.put("/api/vendormachine/users/")
            .contentType(APPLICATION_JSON_VALUE)
            .content(contentNewUSer)
        val value = this.mockmvc.perform(builder)
            .andExpect(ok).andReturn()
        val returned = objectMapper.readValue(value.response.contentAsString, User::class.java)
        assertThat(returned.role).isEqualTo("buyer")
    }

    @Test
    @Throws(Exception::class)
    fun `should delete a user`() {

        val user = userService.createNewUser(newUser)

        val contentNewUSer = objectMapper.writeValueAsString(user)
        val ok = MockMvcResultMatchers.status()
            .isNoContent
        val builder = MockMvcRequestBuilders.delete("/api/vendormachine/users/${user.userid}")
            .contentType(APPLICATION_JSON_VALUE)
            .content(contentNewUSer)
        val value = this.mockmvc.perform(builder)
            .andExpect(ok)
        try {
            userService.getUser(userId = user.userid)
        } catch (e: UserException) {
            assertThat((e.message.equals("User with {$user.userid} does not exist")))
        }
    }
}
