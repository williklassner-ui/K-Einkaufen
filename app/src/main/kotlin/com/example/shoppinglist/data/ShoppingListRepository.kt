package com.example.shoppinglist.data

import com.example.shoppinglist.model.ShoppingItem
import com.example.shoppinglist.model.ShoppingList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ShoppingListRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val listsCollection = db.collection("shoppingLists")

    suspend fun createList(name: String): String {
        val userId = auth.currentUser?.uid ?: return ""
        val list = ShoppingList(name = name, ownerId = userId)
        val doc = listsCollection.document()
        doc.set(list).await()
        return doc.id
    }

    // Simplified - expand as needed
}