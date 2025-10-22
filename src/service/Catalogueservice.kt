package service

import model.*
import repository.Repository
import util.IdGenerator
class Catalogueservice (private val bookRepo:Repository<Book>){
    fun addPrintedBook(title: String, author: String,year:Int,category:String,sumpages:Int, stock:Int){
    val book = PrintedBook(IdGenerator.generateBookId(),title,author,year,category,sumpages,stock)
        bookRepo.save(book)
        println("Book ${book.title} added successfully")
    }
    fun addDigitalBook(title: String, author: String, year:Int, category:String, sizefileMB: Double, format:String){
        val book = DigitalBook(IdGenerator.generateBookId(),title,author,year,category,sizefileMB,format)
        bookRepo.save(book)
        println("Book ${book.title} added successfully")
    }
    fun findBook(query:String):List<Book>{
        return bookRepo.findAll().filter{
            it.title.contains(query,ignoreCase = true)||
            it.author.contains(query, ignoreCase = true)||
            it.category.contains(query,ignoreCase = true)
        }
    }
}