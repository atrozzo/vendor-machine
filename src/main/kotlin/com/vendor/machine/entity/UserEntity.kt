package com.vendor.machine.entity


import com.vendor.machine.model.User
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "app_user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var user_id: Long,
    @Column(name = "username")
    var username: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "authority")
    var role: String,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "role_id")]
    )
    var roleEntities: Set<RoleEntity>? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var account: UserAccountEntity? = null

) {

    companion object UserEntityMapper {
        fun toEntity(user: User): UserEntity {
            return UserEntity(
                username = user.username,
                password = user.password,
                role = user.role,
                user_id = 0,
                account = UserAccountEntity(userId = user.userid, amount = user.amount)
            )
        }
    }
}
