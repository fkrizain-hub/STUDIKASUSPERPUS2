package model

class PrintedBook (
    id:String,title:String,author:String,years:Int,category:String,
    val sumpages: Int,
    var stock:Int
):Book(id,title,author,years,category) {
    override fun info():String {
        return "Printed Book || ID: $id || title: $title || author: $author || Stock: $stock"
    }
}