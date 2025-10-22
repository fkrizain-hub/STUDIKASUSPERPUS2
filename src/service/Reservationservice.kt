package service

import model.Reservation
import java.util.LinkedList
import java.util.Queue

class Reservationservice {
    private val queueReservation = mutableMapOf<String, Queue<String>>()

    fun addReservation(IdBook: String, IdMember: String) {
        val queue = queueReservation.getOrPut(IdBook) { LinkedList() }
        queue.add(IdMember)
        println("Member (ID: $IdMember) Added Reservation for book: (ID:$IdBook).")
    }
    fun getfirstqueue (IdBook: String): String? {
        return queueReservation[IdBook]?.poll()
    }
    fun getlistqueue():Map<String, Queue<String>> = queueReservation
}