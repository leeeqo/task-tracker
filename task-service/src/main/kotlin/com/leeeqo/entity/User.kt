package com.leeeqo.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    private val email: String = "",
    private val password: String = "",

    @Transient private val isEnabled: Boolean = true,
    @Transient private val isCredentialsNonExpired: Boolean = true,
    @Transient private val isAccountNonExpired: Boolean = true,
    @Transient private val isAccountNonLocked: Boolean = true,
    @Transient private val authorities: Set<GrantedAuthority> = setOf(),
) : UserDetails {

    override fun getUsername(): String = email

    override fun getPassword(): String = password

    override fun isEnabled(): Boolean = isEnabled

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired

    override fun isAccountNonExpired(): Boolean = isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = isAccountNonLocked

    override fun getAuthorities(): Set<GrantedAuthority> = authorities
}
