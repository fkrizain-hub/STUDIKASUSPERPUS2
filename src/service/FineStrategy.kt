package service
import java.time.temporal.ChronoUnit
import java.time.LocalDate

interface FineStrategy{
    fun countFine(deadline: LocalDate): Long
}

class FineRegularStrategy: FineStrategy {
    private val rateperDay = 1000L
    override fun countFine(deadline: LocalDate): Long {
        val overdeadline = ChronoUnit.DAYS.between(deadline, LocalDate.now())
        return if(overdeadline > 0) overdeadline * rateperDay else 0
    }
}

class FinePremiumStrategy: FineStrategy {
    private val rateperDay = 500L
    override fun countFine(deadline: LocalDate): Long {
        val overdeadline = ChronoUnit.DAYS.between(deadline, LocalDate.now())
        return if (overdeadline > 0) overdeadline * rateperDay else 0
    }
}

class FineStaffStrategy : FineStrategy {
    override fun countFine(deadline: LocalDate): Long = 0L}