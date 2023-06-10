package com.noteappexample.noteapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noteappexample.noteapp.repository.NoteRepository
import com.noteappexample.noteapp.room.Note
import kotlinx.coroutines.launch

class NoteHomeScreenViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        Log.d("NoteHomeScreenViewModel", "NoteHomeScreenViewModel initialized." )
        viewModelScope.launch {
            getNotes()
        }
    }

      suspend fun getNotes() {
         viewModelScope.launch {
             _notes.value = noteRepository.getNotes()
         }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)

        }
    }

}