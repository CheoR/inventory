package com.example.inventory.ui.navigation

/**
 * Interface describes app navigation destinations
 */
interface NavigationDestination {
    /**
     * Define unique composable path name
     */
    val route: String

    /**
     * String resource id contains displayed screen title.
     */
    val titleRes: Int
}
