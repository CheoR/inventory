package com.example.inventory.home

import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeItemRepository : ItemsRepository {
    private val _item = MutableStateFlow<Item?>(null)
    private val item: StateFlow<Item?> = _item.asStateFlow()

//    private val _items = MutableStateFlow<List<Item>>(emptyList())
    private val _items = mutableListOf<Item>()
    private val items = MutableStateFlow<List<Item>>(emptyList())
//    private val items: StateFlow<List<Item>> = _items.asStateFlow()
//    private val items: Flow<List<Item>> = MutableStateFlow(_items)

    override fun getAllItemsStream(): Flow<List<Item>> {
        return items
    }

    override fun getItemStream(id: Int): Flow<Item?> {
        return item
    }

    override suspend fun insertItem(item: Item) {
        println("insertItem TEST thread: ${Thread.currentThread().name}")

        _items.add(item)
        items.value = _items.toList()
//        _items.value += _items.value.toList() + item
        println("\n\n\tItem inserted: $item")
        println("\n\n\t\tCurrent items size: ${items.value.size}: ${items.value}")
    }

    override suspend fun deleteItem(item: Item) {
//        _items.value -= item
        _items.remove(item)
        items.value = _items.toList()
    }

    override suspend fun updateItem(item: Item) {
        val index = _items.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            _items[index] = item
            items.value = _items.toList()
        }

//        val index = _items.value.indexOfFirst { it.id == item.id }
//        if (index >= 0) {
//            _items.value = _items.value.toMutableList().apply {
//                set(index, item)
//            }
//        }
    }
}
