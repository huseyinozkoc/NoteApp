package com.noteappexample.noteapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val content: String,

    val creationDate: String,

    val lastModifiedDate : String,

    val isPrivate:Boolean = false,

    // For private notes
    val password:String = "",



)