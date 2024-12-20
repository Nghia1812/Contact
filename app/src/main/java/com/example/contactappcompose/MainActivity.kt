package com.example.contactappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.screen.AddContactScreen
import com.example.contactappcompose.screen.ContactDetailScreen
import com.example.contactappcompose.screen.ContactListScreen
import com.example.contactappcompose.screen.EditContactScreen
import com.example.contactappcompose.ui.theme.ContactAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
                ContactApp()
            }

    }
}

@Composable
fun ContactApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list_screen"){
        composable("list_screen"){
            ContactListScreen(navController)
        }
        composable(
            "contact_detail/{contactId}",
            arguments = listOf(
                navArgument("contactId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null) {
                ContactDetailScreen(navController, contactId)
            }
        }
        composable("add_contact_screen"){
            AddContactScreen(navController)
        }
        composable("edit_contact_screen/{contactId}",
            arguments = listOf(
                navArgument("contactId") {
                    type = NavType.IntType
                }
            )
        ){
                backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null) {
                EditContactScreen(navController, contactId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContactApp()
}