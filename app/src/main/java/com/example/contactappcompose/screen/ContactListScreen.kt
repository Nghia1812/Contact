package com.example.contactappcompose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.contactappcompose.appviewmodel.AppVMFactory
import com.example.contactappcompose.appviewmodel.ContactListViewModel
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactDatabase
import com.example.contactappcompose.datasource.ContactRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(navController: NavHostController) {
//    val contactDao = ContactDatabase.getDatabase(LocalContext.current).contactDao()
//    val contactRepository = ContactRepository(contactDao)
//    val viewModelFactory = AppVMFactory(contactRepository)
//    val viewModel: ContactListViewModel = viewModel(factory = viewModelFactory)
    val viewModel: ContactListViewModel = hiltViewModel()
    LaunchedEffect(Unit) { // Runs only once when this Composable enters the composition
        viewModel.getContacts()
    }
    val contacts by viewModel.contactLiveData.observeAsState(emptyList<Contact>())

    var filtered by remember { mutableStateOf(contacts) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Search TextField (like SearchView in XML)
        var searchQuery by remember { mutableStateOf("") }
        filtered = if (searchQuery.isEmpty()) {
            contacts
        } else {
            contacts.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.phone.contains(searchQuery, ignoreCase = true) ||
                        it.email.contains(searchQuery, ignoreCase = true)
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search contacts") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            )
        }

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp)
        ) {
            itemsIndexed(
                items = filtered,
                key = { _, item -> item.hashCode() }
            ) { _, contactContent ->
                // Display each email item
                ContactItem(
                    contactContent,
                    onRemove = { contact -> viewModel.deleteContact(contact) },
                    navController = navController
                )
            }



        }

        // Add Contact Button (positioned at the bottom-right)
        FloatingActionButton(
            onClick = { navController.navigate("add_contact_screen") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+")
        }
    }
}

