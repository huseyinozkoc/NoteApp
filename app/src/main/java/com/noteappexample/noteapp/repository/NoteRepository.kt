package com.noteappexample.noteapp.repository

import androidx.lifecycle.LiveData
import com.noteappexample.noteapp.room.Note
import com.noteappexample.noteapp.room.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}