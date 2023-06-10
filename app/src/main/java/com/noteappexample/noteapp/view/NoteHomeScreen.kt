package com.noteappexample.noteapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.noteappexample.noteapp.R
import com.noteappexample.noteapp.databinding.FragmentNoteDetailScreenBinding
import com.noteappexample.noteapp.databinding.FragmentNoteHomeScreenBinding
import com.noteappexample.noteapp.repository.NoteRepository
import com.noteappexample.noteapp.room.AppDatabase
import com.noteappexample.noteapp.room.Note
import com.noteappexample.noteapp.room.NoteDao
import com.noteappexample.noteapp.viewmodel.NoteDetailScreenViewModel
import com.noteappexample.noteapp.viewmodel.NoteHomeScreenViewModel
import com.noteappexample.noteapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class NoteHomeScreen : Fragment() {

    private var _binding: FragmentNoteHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteHomeScreenViewModel
    private lateinit var factory: ViewModelFactory

    private lateinit var navController: NavController

    private lateinit var noteDao: NoteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteHomeScreenBinding.inflate(inflater, container, false)


        // Get the database instance
        // Use the noteDao to perform database operations
        // For example, you can call noteDao.getNotes() to get the notes
        val appDatabase = AppDatabase.getDatabase(requireContext())
        noteDao = appDatabase.noteDao()


        // Initialize the ViewModel
        val noteRepository = NoteRepository(noteDao) //NoteDao'yu ilk initialize etmemiz gerek.
        factory = ViewModelFactory(noteRepository)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[NoteHomeScreenViewModel::class.java]

        // Initialize NavController
        navController = findNavController()

        // Observe the user data and update the UI
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            // Update the UI with the notes data
            // For example, update a RecyclerView adapter with the note list.
        }




        lifecycleScope.launch {
            async {
                noteDao.insertNote(
                    Note(
                        id = 0, "Titlee1", "Content", "Data", "LastModifiet", isPrivate = false, "",
                    )
                )
            }.await()

            var a = emptyList<Note>()
            async { a = noteDao.getNotes() }.await()

            Log.d("NoteHomeScreen", a[0].title)

            async {
                delay(4000)
                noteDao.deleteNote(
                    Note(
                        id = 1, "Titlee1", "Content", "Data", "LastModifiet", isPrivate = false, "",
                    )
                )

            }.await()

        }




        return binding.root
    }


}