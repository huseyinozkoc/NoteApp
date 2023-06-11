package com.noteappexample.noteapp.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noteappexample.noteapp.R
import com.noteappexample.noteapp.room.Note
import kotlin.random.Random


class NoteAdapter(private val notes: MutableList<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    fun getDataList(): MutableList<Note> {
        return notes
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    // This method is responsible for binding the data to the views in each item
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)

        /*
        // Change background color for each item based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.purple_200)
        } else {
            holder.itemView.setBackgroundResource(R.color.white)
        }

         */
        val randomColor = getRandomColor()
        holder.itemView.setBackgroundColor(randomColor)

    }

    // This method returns the total number of items in the RecyclerView
    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views here (e.g., TextView, ImageView)
        // You can access them using itemView.findViewById() method
        fun bind(note: Note) {
            // Set the note text to a TextView or any other view in your item layout
            val textViewNote = itemView.findViewById<TextView>(R.id.title)
            textViewNote.text = note.title
        }
    }


    private fun getRandomColor(): Int {
        val random = Random.Default
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        return Color.rgb(r, g, b)
    }

}