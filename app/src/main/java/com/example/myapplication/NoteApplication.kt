package com.example.myapplication

import android.app.Application
import com.example.myapplication.Repository.NoteRepository
import com.example.myapplication.Room.NoteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication:Application() {
    val applicationScope= CoroutineScope(SupervisorJob())
    val database by lazy { NoteRoomDatabase.getDataBase(this,applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }
}