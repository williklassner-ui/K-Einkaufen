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

    fun getUserLists(): Flow<List<ShoppingList>> = flow {
        val userId = auth.currentUser?.uid ?: return@flow
        listsCollection.whereArrayContains("sharedWith", userId)
            .addSnapshotListener { snapshot, _ ->
                // For simplicity, emit in flow
            }
        // Note: In production use snapshot listener properly with callbackFlow
    }

    // Simplified for demo; use proper realtime
    suspend fun addItem(listId: String, item: ShoppingItem) {
        val listRef = listsCollection.document(listId)
        listRef.update("items", com.google.firebase.firestore.FieldValue.arrayUnion(item)).await()
    }

    // More methods needed for full CRUD, share, etc.
}