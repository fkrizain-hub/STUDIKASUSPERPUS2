package model

class AudioBook(
    id: String,title: String,author: String,year:Int,category: String,
    val Minuteduration: Int,
    val narrator: String):Book(id, title, author, year, category) {
    override fun info():String {
        return "Audio Book || ID: $id || title: $title || Narrator: $narrator || Duration: $Minuteduration minutes"
    }
}