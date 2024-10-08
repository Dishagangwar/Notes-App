package com.example.myapplication.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class NoteAddActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription:EditText
    lateinit var cancelButton:Button
    lateinit var saveButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_add)
        supportActionBar?.title="Add Note"
        editTextTitle=findViewById(R.id.editTitle)
        editTextDescription =findViewById(R.id.editDescription)
        cancelButton =findViewById(R.id.button_cancel)
        saveButton =findViewById(R.id.button_Save)

         cancelButton.setOnClickListener(){
             finish()
         }
         saveButton.setOnClickListener(){
          saveNote()
         }
    }
    fun saveNote(){
        val noteTitle: String= editTextTitle.text.toString()
        val noteDescription: String=editTextDescription.text.toString()
           val intent = Intent()
        intent.putExtra("title",noteTitle)
        intent.putExtra("description",noteDescription)
        setResult(RESULT_OK,intent)
        finish()
    }
}
