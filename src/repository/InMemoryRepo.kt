package repository

import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KProperty1
import repository.Identifiable

class InMemoryRepo<T : Identifiable>: Repository<T> {
    private val dataStore = mutableMapOf<String, T>()

    override fun save(item: T) {
        dataStore[item.id] = item
    }
    override fun findAll(): List<T> = dataStore.values.toList()
    override fun findById(id:String):T? = dataStore[id]
    override fun delete(id: String) {dataStore.remove(id) }
}