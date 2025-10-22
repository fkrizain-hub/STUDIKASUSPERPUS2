package model
import repository.Identifiable
import java.time.LocalDate

data class Borrowing (
    override val id: String,
    val idBook: String,
    val idMember: String,
    val dateBorrowing: LocalDate,
    val deadline: LocalDate,
    var dateBack: LocalDate? = null
): Identifiable