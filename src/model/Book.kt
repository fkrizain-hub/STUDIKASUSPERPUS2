package model

import repository.Identifiable

abstract class Book(
    override val id: String,
    val title: String,
    val author: String,
    val years:Int,
    val category: String,
): Identifiable {
    abstract fun info():String
}