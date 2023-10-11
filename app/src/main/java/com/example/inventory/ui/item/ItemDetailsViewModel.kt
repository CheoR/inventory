package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve, update and delete item from [ItemsRepository]'s data source.
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    // Retrieve data from item repository and map to ItemDetailsUiState
    val uiState: StateFlow<ItemDetailsUiState> =
        itemsRepository.getItemStream(itemId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(
                    outOfStock = it.quantity <= 0,
                    itemDetails = it.toItemDetails(),
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.itemDetails.toItem()
            if (currentItem.quantity > 0) {
                itemsRepository.updateItem(currentItem.copy(quantity = currentItem.quantity - 1))
            }
        }
    }

    suspend fun deleteItem() {
        // convert uiState from itemDetails type to Item entity type
        itemsRepository.deleteItem(uiState.value.itemDetails.toItem())
    }

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
