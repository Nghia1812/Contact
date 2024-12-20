package com.example.contactappcompose.appviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel()  {
    fun getContactById(contactId: Int) : Contact {
        return runBlocking {
            val deferred = async { contactRepository.getContactById(contactId) }
            deferred.await()  // Wait for the result
        }
    }

}
