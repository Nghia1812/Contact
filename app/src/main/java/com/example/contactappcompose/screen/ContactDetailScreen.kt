package com.example.contactappcompose.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.contactappcompose.R
import com.example.contactappcompose.appviewmodel.AppVMFactory
import com.example.contactappcompose.appviewmodel.ContactAddViewModel
import com.example.contactappcompose.appviewmodel.ContactDetailsViewModel
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactDatabase
import com.example.contactappcompose.datasource.ContactRepository

@Composable
fun ContactDetailScreen(navController: NavHostController, contactId: Int) {

//    val contactDao = ContactDatabase.getDatabase(LocalContext.current).contactDao()
//    val contactRepository = ContactRepository(contactDao)
//    val viewModelFactory = AppVMFactory(contactRepository)
//    val viewModel: ContactDetailsViewModel = viewModel(factory = viewModelFactory)
    val viewModel: ContactDetailsViewModel = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val contact = viewModel.getContactById(contactId)
    val context = LocalContext.current

    val bitmap = contact.imagePath?.let {
        android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            if (bitmap != null) {
                Image(
                    //painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    bitmap = bitmap.asImageBitmap(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                        .clip(CircleShape)
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = "Name: ${contact.name}, Phone: ${contact.phone}, Email: ${contact.email}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Phone: ${contact.phone}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Email: ${contact.email}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }

        Column(modifier = Modifier
            .align(Alignment.BottomEnd)) {
            FloatingActionButton(
                onClick = {
                    navController.navigate("edit_contact_screen/${contact.id}") {
                        popUpTo("contact_detail/${contact.id}") { inclusive = true }
                    }
                          },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Edit")
            }
            FloatingActionButton(
                onClick = {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = android.net.Uri.parse("tel:" + contact.phone)
                    context.startActivity(dialIntent)
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Call")
            }
        }
    }
}