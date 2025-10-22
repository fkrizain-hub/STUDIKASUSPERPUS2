package service

import model.UserAccount
import model.Role
import repository.Repository

class Authenticationservice (
    private val userAccountRepo: Repository<UserAccount>) {
    init{
        userAccountRepo.save(UserAccount("admin","admin","admin123",Role.ADMIN))
        userAccountRepo.save(UserAccount("pustakawan","pustakawan","perpus456",Role.LIBRARIAN))
    }
    fun login(username: String, passwordInput: String): UserAccount? {
        val account = userAccountRepo.findAll().find{it.username == username}
        if (account != null && account.isPasswordCorrect(passwordInput)) {
            println("\n Login successful! welcome to ${account.username}${account.role}!")
            return account
        }else{
            println("Username or password does not match username or password! Try Again\n")
            return null
        }
    }
}