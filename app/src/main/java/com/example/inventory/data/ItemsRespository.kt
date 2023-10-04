package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository provides insert, update, delete, and retrieve of [Item] from given data source.
 */
interface ItemsRepository {
    /**
     * Retrieve all items from given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Retrieve item from given data source that matches with [id].
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Insert item into data source
     */
    suspend fun insertItem(item: Item)

    /**
     * Delete item from data source
     */
    suspend fun deleteItem(item: Item)

    /**
     * Update item in data source
     */
    suspend fun updateItem(item: Item)
}
