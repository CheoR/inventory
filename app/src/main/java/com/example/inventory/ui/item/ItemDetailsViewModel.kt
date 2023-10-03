package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel to retrieve, update and delete item from [ItemsRepository]'s data source.
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * ItemDetailsScreen UI state
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)
