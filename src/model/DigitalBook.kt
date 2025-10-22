package model

class DigitalBook (
    id: String,title: String,author: String,years:Int,category: String,
    val sizefileMB: Double,
    val digitalformat: String): Book(id,title,author,years,category) {
    override fun info():String {
        return "Digital Book || ID: $id || title: $title || author: $author || Format: $digitalformat"
    }
}