package model

import repository.Identifiable

class UserAccount (
    override val id:String,
    val username: String,
    private val passwordHash: String,
    val role: Role,
    val idMemberrelated: String? = null
): Identifiable {
    fun isPasswordCorrect(passwordToCheck: String): Boolean {
        return this.passwordHash == passwordToCheck
    }
}