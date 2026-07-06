package com.example.shoppinglist.model

import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class ShoppingList(
    @DocumentId val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val ownerId: String = "",
    val sharedWith: List<String> = emptyList(),
    val items: List<ShoppingItem> = emptyList()
)

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val quantity: Int = 1,
    val checked: Boolean = false
)