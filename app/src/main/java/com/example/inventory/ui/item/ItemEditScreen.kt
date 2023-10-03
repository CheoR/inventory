package com.example.inventory.ui.item

import com.example.inventory.R
import com.example.inventory.ui.navigation.NavigationDestination

object ItemEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}
