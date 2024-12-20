package com.example.contactappcompose.appviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactAddViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {
    private val _contactLiveData = MutableLiveData<List<Contact>>()
    val contactLiveData: LiveData<List<Contact>> = _contactLiveData

    private fun getContacts() {
        viewModelScope.launch {
            val contacts = contactRepository.getContacts()
            _contactLiveData.value = contacts
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactRepository.addContact(contact)
            getContacts()
        }
    }
}