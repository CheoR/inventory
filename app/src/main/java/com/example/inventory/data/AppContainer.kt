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
        OfflineItemsRepository()
    }
}
