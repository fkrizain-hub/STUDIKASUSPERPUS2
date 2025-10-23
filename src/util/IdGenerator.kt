package util

object IdGenerator {
    private var bookCounter = 0
    private var memberCounter = 0
    private var borrowingCounter = 0

    fun generateBookId(): String {
        bookCounter++
        return "BK-${String.format("%04d", bookCounter)}"
    }
    fun generateMemberId(): String {
        memberCounter++
        return "AG-${String.format("%04d", memberCounter)}"
    }
    fun generateBorrowingId(): String {
        borrowingCounter++
        return "Borrowing-${String.format("%04d", borrowingCounter)}"
    }
}