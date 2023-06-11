package com.noteappexample.noteapp.view.adapters

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.noteappexample.noteapp.R
import com.noteappexample.noteapp.room.Note
import java.util.*
import kotlin.random.Random


class NoteAdapter(private val notes: MutableList<Note>, var mContext: Context) :
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

        holder.itemView.findViewById<TextView>(R.id.content).setOnClickListener {
            Toast.makeText(mContext, "Content'e tıklandı", Toast.LENGTH_SHORT).show()
        }
        val title= holder.itemView.findViewById<TextView>(R.id.title)
        title.text = note.title

        val content= holder.itemView.findViewById<TextView>(R.id.content)
        content.text = note.content

        val creationDate= holder.itemView.findViewById<TextView>(R.id.creationDate)
        creationDate.text = note.creationDate


        val imageUrlView= holder.itemView.findViewById<ImageView>(R.id.imageUrl)
         if(note.imageUrl.isNotEmpty()) {
             Glide.with(mContext)
                 .load(note.imageUrl) // image url
                 .placeholder(R.drawable.ic_baseline_note_add_24) // any placeholder to load at start
                 .error(R.drawable.ic_baseline_add_24)  // any image in case of error
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .override(200, 200) // resizing
                 .centerCrop()
                 .into(imageUrlView);  // imageview object
         } else {
             Glide.with(mContext)
                 .load("https://www.zbrushcentral.com/uploads/default/original/4X/2/d/b/2db1a3bdf28145cbbce60d7b58cf3d442215890a.jpeg") // image url
                 .placeholder(R.drawable.ic_baseline_note_add_24) // any placeholder to load at start
                 .error(R.drawable.ic_baseline_add_24)  // any image in case of error
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .override(200, 200) // resizing
                 .centerCrop()
                 .into(imageUrlView);  // imageview object
         }


        if (note.isPrivate) {
            val privateImage= holder.itemView.findViewById<ImageView>(R.id.privateImage)
            privateImage.visibility = View.VISIBLE
        }

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