package repository

interface Repository<T> {
    fun save(item: T)
    fun findAll(): List<T>
    fun findById(id: String): T?
    fun delete(id: String)
}
interface Identifiable{
    val id: String
}