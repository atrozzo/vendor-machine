package com.vendor.machine.controller

import com.vendor.machine.model.User
import com.vendor.machine.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/api/vendormachine"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UsersResource @Autowired constructor(
    val userService: UserService
) {

    @Operation(summary = "Create a new User for the Vendor Machine.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User successfully created"),
            ApiResponse(responseCode = "500", description = "Unable to create a new user")
        ]
    )
    @PostMapping(
        path = ["/users/"]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun createAUser(@NotNull @Valid @RequestBody user: User): User {
        return userService.createNewUser(user)
    }

    @Operation(summary = "Update a User ")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User successfully updated"),
            ApiResponse(responseCode = "500", description = "Unable to update the user")
        ]
    )
    @PutMapping(
        path = ["/users/"]
    )
    @ResponseStatus(HttpStatus.OK)
    fun updateAUser(@NotNull @Valid @RequestBody user: User): User {
        return userService.updateUser(user)
    }

    @Operation(summary = "Delete an existing User .")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "User successfully deleted"),
            ApiResponse(responseCode = "400", description = "User not found")
        ]
    )
    @DeleteMapping(
        path = ["/users/{userId}"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAUser(@PathVariable userId: Long) {
        userService.deleteUser(userId)
    }

    @Operation(summary = "Retrieve a single existing User by username")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found"),
            ApiResponse(responseCode = "400", description = "User not found")
        ]
    )
    @GetMapping(
        path = ["/users/{userId}"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.OK)
    fun getAUser(@PathVariable userId: Long): User {
        return userService.getUser(userId)
    }

    @Operation(summary = "Get all available existing users .")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Users foud"),
        ]
    )
    @GetMapping(
        path = ["/users/"]
    )
    // @PreAuthorize("hasRole('ROLE_SELLER')")
    @ResponseStatus(HttpStatus.OK)
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }
}
