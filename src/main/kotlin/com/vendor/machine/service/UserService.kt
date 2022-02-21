package com.vendor.machine.service

import com.vendor.machine.entity.UserEntity
import com.vendor.machine.model.User
import com.vendor.machine.repository.UserRepository
import com.vendor.machine.service.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
) {

    fun createNewUser(user: User): User {
        return User.toModel(userRepository.save(UserEntity.toEntity(user)))
    }

    fun updateUser(user: User): User {
        var entityUser = userRepository.findById(user.userid).orElseThrow { UserException("User with {${user.userid}} does not exist") }
        entityUser.username = user.username
        entityUser.password = user.password
        entityUser.role = user.role
        return User.toModel(userRepository.save(entityUser))
    }

    fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }

    fun getUser(userId: Long): User {
        val user = userRepository.findById(userId)
        return User.toModel(
            user.orElseThrow { UserException("User with {$userId} does not exist") }
        )
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll().map { u -> User.toModel(u) }
    }
}
