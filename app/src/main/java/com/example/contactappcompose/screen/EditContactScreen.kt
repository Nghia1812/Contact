package com.example.contactappcompose.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.contactappcompose.appviewmodel.AppVMFactory
import com.example.contactappcompose.appviewmodel.ContactAddViewModel
import com.example.contactappcompose.appviewmodel.ContactEditViewModel
import com.example.contactappcompose.appviewmodel.ContactListViewModel
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactDatabase
import com.example.contactappcompose.datasource.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditContactScreen(navController: NavHostController, contactId: Int) {
//    val contactDao = ContactDatabase.getDatabase(LocalContext.current).contactDao()
//    val contactRepository = ContactRepository(contactDao)
//    val viewModelFactory = AppVMFactory(contactRepository)
//    val viewModel: ContactEditViewModel = viewModel(factory = viewModelFactory)
    val viewModel: ContactEditViewModel = hiltViewModel()
    val receivedContact: Contact = viewModel.getContactById(contactId)

    var name by remember { mutableStateOf(receivedContact.name) }
    var phone by remember { mutableStateOf(receivedContact.phone) }
    var email by remember { mutableStateOf(receivedContact.email) }


    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    val context = LocalContext.current

    bitmap = receivedContact.imagePath?.let {
        android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture (ImageView equivalent)
        bitmap?.asImageBitmap()?.let {
            Image(
                //painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                bitmap = it,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
                    .clip(CircleShape)
            )
        }

        // Contact Fields (EditTexts inside a vertical LinearLayout)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name Field
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Phone Field
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Enter Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Action Buttons (Save and Cancel)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    val updatedContact = receivedContact.copy(name = name, phone = phone, email = email)
                    viewModel.updateContact(updatedContact)
                    navController.navigate("contact_detail/${receivedContact.id}"){
                        popUpTo("edit_contact_screen/${receivedContact.id}") { inclusive = true }
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Save")
            }
            Button(
                onClick = {
                    //navController.navigate("contact_detail/${receivedContact.id}")
                    navController.navigateUp()
                }
            ) {
                Text("Cancel")
            }
        }
    }
}