package com.example.myapplication.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.NoteAdapter
import com.example.myapplication.Model.Note
import com.example.myapplication.NoteApplication
import com.example.myapplication.R
import com.example.myapplication.ViewModel.NoteViewModel

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.title="Notes App"
        val recyclerView:RecyclerView =findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter= NoteAdapter(this)
        recyclerView.adapter=noteAdapter
        //register activity for result
        registerActivityResultLauncher()

        val viewModelFactory= NoteViewModel.NoteViewModelFactory((application as NoteApplication).repository)
            noteViewModel=ViewModelProvider(this,viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.myAllNotes.observe(this, Observer { notes ->
          //update UI
            noteAdapter.setNote(notes)

        })
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
    }
    fun registerActivityResultLauncher(){
        addActivityResultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNote ->
                val resultCode=resultAddNote.resultCode
                val data=resultAddNote.data
                if (resultCode== RESULT_OK && data!= null){
                    val noteTitle:String=data.getStringExtra("title").toString()
                    val noteDescription:String=data.getStringExtra("description").toString()
                    val note= Note(noteTitle,noteDescription)
                    noteViewModel.insert(note)
                }


            })
        updateActivityResultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultUpdateNote ->
                val resultCode=resultUpdateNote.resultCode
                val data=resultUpdateNote.data
                if (resultCode== RESULT_OK && data!= null){
                    val updatedTitle:String = data.getStringExtra("updatedTitle").toString()
                    val updatedDescription:String = data.getStringExtra("updatedDescription").toString()
                    val noteId=data.getIntExtra("noteId",-1)
                    val newNote=Note(updatedTitle,updatedDescription)
                    newNote.id = noteId
                    noteViewModel.update(newNote)
                }


            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_note -> {
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.delete_all_items -> {
                  showDialogMessage()
            }
        }
        return true
    }
    fun showDialogMessage(){
        val dialogMessage= AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete all notes")
        dialogMessage.setMessage("If click yes all notes will delete"+
        "if you want to delete a specific note, please swip left or right")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            noteViewModel.deleteAllNotes()
        })
        dialogMessage.create().show()

    }
}