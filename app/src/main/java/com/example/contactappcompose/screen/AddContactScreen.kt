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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.contactappcompose.R
import com.example.contactappcompose.appviewmodel.AppVMFactory
import com.example.contactappcompose.appviewmodel.ContactAddViewModel
import com.example.contactappcompose.appviewmodel.ContactEditViewModel
import com.example.contactappcompose.appviewmodel.ContactListViewModel
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactDatabase
import com.example.contactappcompose.datasource.ContactRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddContactScreen(navController: NavHostController){
    //val contactDao = ContactDatabase.getDatabase(LocalContext.current).contactDao()
    //val contactRepository = ContactRepository(contactDao)
    //val viewModelFactory = AppVMFactory(contactRepository)
    val viewModel: ContactAddViewModel = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var selectedImage: ByteArray? by remember { mutableStateOf(null) }

    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    val context = LocalContext.current

    fun newImageUpload(imageUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                selectedImage = context.contentResolver?.openInputStream(imageUri)?.readBytes()
                bitmap = selectedImage?.let {
                    BitmapFactory.decodeByteArray(
                        selectedImage,0, it.size
                    )
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()){ uri ->
            // single image upload function
            uri?.let { newImageUpload(it) }
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

        // Add Picture Button
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Add Picture")
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
                    val newContact = Contact(name = name, phone = phone, email = email, imagePath = selectedImage)
                    viewModel.addContact(newContact)
                    navController.navigate("list_screen"){
                        popUpTo("add_contact_screen") { inclusive = true }
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Add")
            }
            Button(
                onClick = {
                    name = ""
                    phone = ""
                    email = ""
                    navController.navigateUp()
                }
            ) {
                Text("Cancel")
            }
        }
    }
}


