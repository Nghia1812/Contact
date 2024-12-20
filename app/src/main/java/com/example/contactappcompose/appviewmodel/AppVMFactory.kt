package com.example.contactappcompose.appviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactappcompose.datasource.ContactRepository

class AppVMFactory(private val contactsRepository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ContactDetailsViewModel::class.java) -> {
                ContactDetailsViewModel(contactsRepository) as T
            }
            modelClass.isAssignableFrom(ContactEditViewModel::class.java) -> {
                ContactEditViewModel(contactsRepository) as T
            }
            modelClass.isAssignableFrom(ContactAddViewModel::class.java) -> {
                ContactAddViewModel(contactsRepository) as T
            }
            modelClass.isAssignableFrom(ContactListViewModel::class.java) -> {
                ContactListViewModel(contactsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}