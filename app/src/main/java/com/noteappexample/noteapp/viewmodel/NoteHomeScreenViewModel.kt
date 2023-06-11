package com.noteappexample.noteapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noteappexample.noteapp.repository.NoteRepository
import com.noteappexample.noteapp.room.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteHomeScreenViewModel(private val noteRepository: NoteRepository) : ViewModel() {


    val notes: LiveData<List<Note>> = noteRepository.getNotes()

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO)  {
            noteRepository.deleteNote(note)

        }
    }

}