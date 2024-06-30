package hu.aut.bme.springbootalf.model

import hu.aut.bme.springbootalf.enums.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(name = "username")
    var username: String

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    var email: String

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory")
    var password: String

    @Column(name = "balance")
    @Min(value = 0, message = "Balance must be positive")
    var balance: Double = 0.0

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "username")])
    var roles: List<UserRole> = listOf(UserRole.ROLE_USER)

    @Column(name = "enabled")
    var enabled: Boolean = true

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
        name = "user_books",
        joinColumns = [JoinColumn(name = "username")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    var books: Set<Book> = setOf()

    @Column(name= "password_last_changed")
    var passwordLastChanged: Long = System.currentTimeMillis()

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + username.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val otherUser = other as User
        return username == otherUser.username
    }

    constructor(username: String, email: String, password: String, roles: List<UserRole>) {
        this.username = username
        this.email = email
        this.password = password
        this.roles = roles
    }
    fun toSafeString(): String {
        return "User(username=$username, email=$email, balance=$balance, roles=$roles, enabled=$enabled)"
    }
}