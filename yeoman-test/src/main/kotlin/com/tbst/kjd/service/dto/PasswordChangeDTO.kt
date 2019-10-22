package com.tbst.kjd.service.dto

/**
 * A DTO representing a password change required data - current and new password.
 */
data class PasswordChangeDTO(var currentPassword: String? = null, var newPassword: String? = null)
