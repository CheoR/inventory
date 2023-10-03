package com.example.inventory.data

/**
 * Entity data class represents single database row.
 */
class Item(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
