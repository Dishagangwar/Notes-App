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

class UpdateActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var cancelButton: Button
    lateinit var saveButton: Button
    var currentId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        supportActionBar?.title= "Update Note"
        editTextTitle=findViewById(R.id.editTitleUpdate)
        editTextDescription =findViewById(R.id.editDescriptionUpdate)
        cancelButton =findViewById(R.id.button_cancelUpdate)
        saveButton =findViewById(R.id.button_SaveUpdate)
        getAndSet()
        cancelButton.setOnClickListener(){
            finish()
        }
        saveButton.setOnClickListener(){
            updateNote()
            
        }

    }
    fun updateNote(){
         val updatedTitle= editTextTitle.text.toString()
        val updatedDescription=editTextDescription.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle",updatedTitle)
        intent.putExtra("updatedDescription",updatedDescription)
        if (currentId!=-1){
            intent.putExtra("noteId",currentId)
              setResult(RESULT_OK,intent)

        }
             finish()

    }
    fun getAndSet(){
        val currentTitle=intent.getStringExtra("currentTitle")
        val currentDescription=intent.getStringExtra("currentDescription")
        currentId= intent.getIntExtra("currentId",-1)

        editTextTitle.setText(currentTitle)
        editTextDescription.setText(currentDescription)
    }
}