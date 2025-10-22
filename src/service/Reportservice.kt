package service

import model.*
import repository.Repository
import java.io.File

class Reportservice (
    private val borrowRepo: Repository<Borrowing>,
    private val bookRepo: Repository<Book>,
    private val reservationService: Reservationservice
) {
    fun showactiveborrow(){
        println("\n--- Active Borrow Report ---")
        val listborrow=borrowRepo.findAll()
        if (listborrow.isEmpty()){
            println("No book borrowed")
            return
        }
        listborrow.forEach {
            val booktitle = bookRepo.findById(it.idBook)?.title ?: "N/A"
            println("ID Borrow: ${it.id} | Book: $booktitle | Borrower ID: ${it.idMember} | Deadline: ${it.deadline}")
        }
    }
    fun showtopbook(){
        println("\n--- Top Book Report ---")
        val frekuensi = borrowRepo.findAll()
            .groupingBy { it.idBook }.eachCount().toList().sortedByDescending { it.second }.take(3)
        frekuensi.forEachIndexed { index,(idBook,sum) ->
            val booktitle = bookRepo.findById(idBook)?.title ?: "N/A"
            println("${index + 1}. $booktitle (Borrowed $sum times")
        }
    }
    fun showqueuereservation(){
        println("\n--- Queue Report ---")
        val queue = reservationService.getlistqueue()
        if (queue.isEmpty()){
            println("No queue reservation")
            return
        }
        queue.forEach {(idBook,listmember) ->
            val booktitle = bookRepo.findById(idBook)?.title ?: "N/A"
            println("Book  '$booktitle': ${listmember.size} people queued (IDs: ${listmember.joinToString()})")
        }
    }
    fun exportreportborrowtocsv(){
        val listborrow=borrowRepo.findAll()
        if (listborrow.isEmpty()){
            println("No data to export.")
            return
        }
        val csvHeader = "ID_BORROW,ID_BOOK,ID_MEMBER,DATE_BORROW,DEADLINE \n"
        val csvRows = listborrow.map {
            val booktitle = bookRepo.findById(it.idBook)?.title ?: "N/A"
            "${it.id},${it.idBook},\"$booktitle\",${it.idMember},${it.dateBorrowing},${it.deadline}"
        }
        try{
            File("report_borrowing.csv").writeText(csvHeader + csvRows.joinToString("\n"))
            println("Report successfully exported to report_borrowing.csv")
        } catch (e: Exception) {
            println("Error: failed to export report_borrowing.csv ${e.message}")
        }
    }
}