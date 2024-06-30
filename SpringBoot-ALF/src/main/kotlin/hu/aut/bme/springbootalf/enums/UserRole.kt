package hu.aut.bme.springbootalf.enums

/**
 * Enumeration representing different roles of users in the application.
 * These roles are used to define access levels and permissions.
 */
enum class UserRole {
    ROLE_USER,                      // Role representing a regular user.
    ROLE_ADMIN                      // Role representing an administrator with elevated privileges.
}