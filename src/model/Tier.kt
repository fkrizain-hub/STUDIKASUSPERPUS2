package model

enum class Tier (val limitBorrows: Int, val borrowforlongtime: Int) {
    REGULAR(3,14),
    PREMIUM(5,21),
    STAFF(7,28)
}