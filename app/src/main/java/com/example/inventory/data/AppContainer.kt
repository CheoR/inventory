package com.example.inventory.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * [AppContainer] implementation provides [OfflineItemsRepository] instance
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * [ItemsRepository] implementation
     */
    override val itemsRepository: ItemsRepository by lazy {
        // Call getDatabase() to instantiate database instance
        // on InventoryDatabase class passing in context and call
        // .itemDao() to create Dao instance
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}
