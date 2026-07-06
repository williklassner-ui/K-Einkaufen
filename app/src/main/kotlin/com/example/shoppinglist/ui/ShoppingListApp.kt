package com.example.shoppinglist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.model.ShoppingItem
import com.example.shoppinglist.model.ShoppingList
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun ShoppingListApp() {
    val auth = FirebaseAuth.getInstance()
    var currentUser by remember { mutableStateOf(auth.currentUser) }
    var lists by remember { mutableStateOf(listOf<ShoppingList>()) }
    var currentList by remember { mutableStateOf<ShoppingList?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Sign in anonymously for demo
        if (currentUser == null) {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser = auth.currentUser
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einkaufsliste") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            if (currentList != null) {
                FloatingActionButton(onClick = { /* add item dialog */ }) {
                    Text("+")
                }
            } else {
                FloatingActionButton(onClick = { /* create new list */ }) {
                    Text("Neue Liste")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (currentList == null) {
                // List of lists
                Button(onClick = {
                    // Create new list demo
                    scope.launch {
                        // repository call
                    }
                }) {
                    Text("Neue Einkaufsliste erstellen")
                }
            } else {
                // Items list
                LazyColumn {
                    items(currentList!!.items) { item ->
                        ShoppingItemRow(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingItemRow(item: ShoppingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.checked,
            onCheckedChange = { /* toggle */ }
        )
        Text(
            text = "${item.name} (${item.quantity})",
            modifier = Modifier.weight(1f),
            textDecoration = if (item.checked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}