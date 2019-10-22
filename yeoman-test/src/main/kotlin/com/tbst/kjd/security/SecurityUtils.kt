@file:JvmName("SecurityUtils")

package com.tbst.kjd.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

import java.util.Optional

/**
 * Get the login of the current user.
 *
 * @return the login of the current user.
 */
fun getCurrentUserLogin(): Optional<String> =
    Optional.ofNullable(SecurityContextHolder.getContext().authentication)
        .map { authentication ->
            when (val principal = authentication.principal) {
                is UserDetails -> principal.username
                is String -> principal
                else -> null
            }
        }

/**
 * Get the JWT of the current user.
 *
 * @return the JWT of the current user.
 */
fun getCurrentUserJWT(): Optional<String> =
    Optional.ofNullable(SecurityContextHolder.getContext().authentication)
        .filter { it.credentials is String }
        .map { it.credentials as String }

/**
 * Check if a user is authenticated.
 *
 * @return true if the user is authenticated, false otherwise.
 */
fun isAuthenticated(): Boolean =
    Optional.ofNullable(SecurityContextHolder.getContext().authentication)
        .map { 
            val authorities = it.authorities
            authorities.none { authority -> authority.authority == ANONYMOUS }
        }
        .orElse(false)

/**
 * If the current user has a specific authority (security role).
 *
 * The name of this method comes from the `isUserInRole()` method in the Servlet API
 *
 * @param authority the authority to check.
 * @return true if the current user has the authority, false otherwise.
 */
fun isCurrentUserInRole(authority: String): Boolean =
    Optional.ofNullable(SecurityContextHolder.getContext().authentication)
        .map { 
            val authorities = it.authorities
            authorities.any { auth -> auth.authority.equals(authority) }
        }
        .orElse(false)

