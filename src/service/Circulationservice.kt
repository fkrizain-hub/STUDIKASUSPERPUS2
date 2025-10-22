package service

import model.*
import repository.Repository
import util.IdGenerator
import java.time.LocalDate
import java.time.LocalDateTime

class Circulationservice (
    private val bookRepo: Repository<Book>,
    private val memberRepo: Repository<Member>,
    private val borrowRepo: Repository<Borrowing>,
    private val reservationService: Reservationservice
) {
    fun borrowingbook(idMember:String,idBook: String){
        val book = bookRepo.findById(idBook) ?: run{println("Error book not found");return }
        val member = memberRepo.findById(idMember) ?: run{println("Error member not found");return }
        val activeborrow = borrowRepo.findAll().count{it.idMember == idMember}
        if (activeborrow >= member.tier.limitBorrows){
            println("Error member ${member.name} has reached maximum limit to borrow ${member.tier.limitBorrows} books.")
            return
        }
        when(book){
            is PrintedBook -> {
                if(book.stock>0){
                    book.stock--
                    bookRepo.save(book)
                    val deadline = LocalDate.now().plusDays(member.tier.borrowforlongtime.toLong())
                    val borrow = Borrowing(IdGenerator.generateBorrowingId(),idBook,idMember,deadline, LocalDate.now())
                    borrowRepo.save(borrow)
                    println("'${book.title}' succesfully borrowed by ${member.name} deadline: ${deadline}.")
                }else{
                    println("${book.title} out of stock.")
                    reservationService.addReservation(idBook,idMember)
                }
            }
            is DigitalBook, is AudioBook -> {
                val deadline = LocalDate.now().plusDays(member.tier.borrowforlongtime.toLong())
                val borrow = Borrowing(IdGenerator.generateBorrowingId(),idBook,idMember, LocalDate.now(),deadline)
                borrowRepo.save(borrow)
                println("${book.title} succesfully accessed by ${member.name} access will be over on: ${deadline}.")
            }
        }
    }
    fun givebackbook(idBook:String,idMember:String){
        val borrow = borrowRepo.findAll().find{it.idBook==idBook && it.idMember == idMember}?: run {println("Error borrow not found");return}
        val book = bookRepo.findById(idBook)!!
        val member = memberRepo.findById(idMember)!!

        val fineStrategy = when (member.tier){
            Tier.REGULAR -> FineRegularStrategy()
            Tier.PREMIUM -> FinePremiumStrategy()
            Tier.STAFF -> FineStaffStrategy()
        }
        val fine = fineStrategy.countFine(borrow.deadline)
        if(fine>0){
            println("Overdeadline! must be pay: Rp $fine")
        }
        if (book is PrintedBook){
            book.stock++
            bookRepo.save(book)
            println("Printed book ${book.title} already returned")

            val idReservation = reservationService.getfirstqueue(idBook)
            if (idReservation != null) {
                val nameReservation = memberRepo.findById(idReservation)?.name ?:"N/A"
                println("INFO: This book on queue will be offered to $nameReservation (ID: $idReservation).")
            }
            }else{
                println("Access to ${book.title} has done.")
            }
            borrowRepo.delete(borrow.id)
        }
    }