package com.example.contactappcompose.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contactappcompose.datasource.Contact

@Dao
interface ContactDao {
    @Insert
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY name ASC")
    suspend fun getContacts(): List<Contact>

    @Query("SELECT * FROM contact WHERE id = :contactId")
    suspend fun getContactById(contactId: Int): Contact

    @Query("SELECT * FROM contact WHERE id = :contactId")
    fun getContactByIdLive(contactId: Int): LiveData<Contact>

}