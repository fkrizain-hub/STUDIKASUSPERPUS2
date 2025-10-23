package service

import model.Reservation
import java.util.LinkedList
import java.util.Queue

class Reservationservice {
    private val queueReservation = mutableMapOf<String, Queue<String>>()

    fun addReservation(IdBook: String, IdMember: String) {
        print("Apakah Anda ingin menambahkan reservasi untuk buku (ID: $IdBook)? (y/n): ")
        val input = readlnOrNull()?.lowercase()

        if (input == "y") {
            val queue = queueReservation.getOrPut(IdBook) { LinkedList() }
            queue.add(IdMember)
            println("✅ Member (ID: $IdMember) berhasil ditambahkan ke antrean reservasi untuk buku (ID: $IdBook).")
        } else if (input == "n") {
            println("❌ Reservasi dibatalkan, kembali ke menu utama.")
        } else {
            println("⚠️ Input tidak valid. Silakan ulangi perintah.")
        }
    }
    fun getfirstqueue (IdBook: String): String? {
        return queueReservation[IdBook]?.poll()
    }
    fun getlistqueue():Map<String, Queue<String>> = queueReservation
}


