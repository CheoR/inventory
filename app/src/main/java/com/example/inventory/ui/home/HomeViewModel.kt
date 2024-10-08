package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all Room database items.
 */


class HomeViewModel(itemsRepository: ItemsRepository): ViewModel() {
    // retrieve all items in Room database as StateFlow observable API for UI state.
    // When Room Inventory data changes, UI updates automatically.
    /**
     * Holds home ui state. The list of items are retrieved from [ItemsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        // constantly updadating state
        itemsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    init { println("HomeViewModel refreshed: ${homeUiState.value}") }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * HomeScreen Ui State
 */
data class HomeUiState(val itemList: List<Item> = listOf())
