package com.example.myapplication.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.Model.Note
import java.util.concurrent.Flow

@Dao
interface NoteDAO
{
    @Insert
  suspend fun insert(note:Note)
  @Update
  suspend fun update(note: Note)
  @Delete
  suspend fun delete(note: Note)
  @Query("DELETE FROM note_table")
  suspend fun deleteAllNotes()
  @Query("SELECT * FROM note_table ORDER BY id ASC")
  fun getAllNotes():kotlinx.coroutines.flow.Flow<List<Note>>
}