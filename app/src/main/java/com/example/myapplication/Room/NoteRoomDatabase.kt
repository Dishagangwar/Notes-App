package com.example.myapplication.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase:RoomDatabase() {
    abstract fun getNoteDao():NoteDAO
    //singleton
    companion object{
        @Volatile
        private var INSTANCE:NoteRoomDatabase?=null
        fun getDataBase(context: Context,scope: CoroutineScope):NoteRoomDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,
                    NoteRoomDatabase::class.java,"note_database")
                    .addCallback(NoteDataBaseCallback(scope))
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }
    private class NoteDataBaseCallback(private val scope:CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                  scope.launch {
                      val noteDao =database.getNoteDao()
                      noteDao.insert(Note("Title 1","Description 1"))
                      noteDao.insert(Note("Title 2","Description 2"))
                      noteDao.insert(Note("Title 3","Description 3"))
                  }
            }
        }
    }
}