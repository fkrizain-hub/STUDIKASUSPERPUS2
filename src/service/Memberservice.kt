package service

import model.*
import repository.Repository
import util.IdGenerator

class Memberservice(private val memberRepo: Repository<Member>,
    private val userAccountRepo: Repository<UserAccount>){
    fun addMember(name: String, tier:Tier){
        val newMemberId = IdGenerator.generateMemberId()
        val newMember = Member(newMemberId,name,tier)
        memberRepo.save(newMember)
        println(" New Member ${newMember.name} with ID: ${newMemberId} tier ${tier.name} added successfully")

        val newUsername = name.lowercase().replace(" ","")
        val defaultPassword = "123456"
        val newAccount = UserAccount(newUsername, newUsername, defaultPassword, role = Role.MEMBER,
            idMemberrelated = newMemberId)
        userAccountRepo.save(newAccount)
        println("Account login for ${newMember.name} has been made (Username: ${newUsername},Pass: ${defaultPassword}).")
    }
}