package com.example.inventory.ui.item

import com.example.inventory.R
import com.example.inventory.ui.navigation.NavigationDestination

object ItemDetailsDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.item_detail_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}
