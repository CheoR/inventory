package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item

/**
 * ViewModel to retrieve all Room database items.
 */
class HomeViewModel : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * HomeScreen Ui State
 */
data class HomeUiState(val itemList: List<Item> = listOf())
