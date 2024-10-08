package com.example.myapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Note
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.UpdateActivity

class NoteAdapter(private val activity:MainActivity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    var notes:List<Note> =ArrayList()
class NoteViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textViewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription:TextView = itemView.findViewById(R.id.textViewDescription)
        val cardView:CardView= itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view:   View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote:Note = notes[position]
        holder.textViewTitle.text=currentNote.title
        holder.textViewDescription.text=currentNote.description
        holder.cardView.setOnClickListener(){
            val intent = Intent(activity,UpdateActivity::class.java)
            intent.putExtra("currentTitle",currentNote.title)
            intent.putExtra("currentDescription",currentNote.description)
            intent.putExtra("currentId",currentNote.id)
            //activity result launcher
            activity.updateActivityResultLauncher.launch(intent)

        }
    }
    fun setNote(myNote: List<Note>){
        this.notes=myNote
        notifyDataSetChanged()
    }
    fun getNote(position: Int):Note{
        return notes[position]

    }

}