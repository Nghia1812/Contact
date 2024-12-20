package com.example.contactappcompose.datasource

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                )
                    .fallbackToDestructiveMigration()
                    //.addCallback(SeedDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
//    private class SeedDatabaseCallback(
//        private val context: Context
//    ) : Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            Log.d("ContactDatabase", "onCreate")
//            val dao = getDatabase(context).contactDao()
//            // Prepopulate with initial data
//            CoroutineScope(Dispatchers.IO).launch {
//                dao.addContact(Contact(name = "Nghia 1", phone = "Artist 1", email = "nghia@1", imagePath = null))
//                dao.addContact(Contact(name = "Nghia 2", phone = "Artist 2", email = "nghia@2", imagePath = null))
//                dao.addContact(Contact(name = "Nghia 3", phone = "Artist 3", email = "nghia@3", imagePath = null))
//            }
//            Log.d("ContactDatabase", "onAddContact")
//        }
//    }
}