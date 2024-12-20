package com.example.contactappcompose.datasource

import androidx.lifecycle.LiveData
import com.example.contactappcompose.datasource.Contact
import com.example.contactappcompose.datasource.ContactDao
import javax.inject.Inject

class ContactRepository (private val contactDao: ContactDao) {
    suspend fun getContacts(): List<Contact> = contactDao.getContacts()
    suspend fun addContact(contact: Contact) = contactDao.addContact(contact)
    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)
    suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)
    suspend fun getContactById(contactId: Int): Contact = contactDao.getContactById(contactId)
    suspend fun getContactByIdLive(contactId: Int): LiveData<Contact> = contactDao.getContactByIdLive(contactId)
}