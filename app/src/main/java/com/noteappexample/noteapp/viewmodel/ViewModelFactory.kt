package com.noteappexample.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noteappexample.noteapp.repository.NoteRepository

class ViewModelFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return NoteHomeScreenViewModel(noteRepository) as T

    }
}