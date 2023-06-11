package com.noteappexample.noteapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.noteappexample.noteapp.R
import com.noteappexample.noteapp.databinding.FragmentAddNoteScreenBinding
import com.noteappexample.noteapp.room.AppDatabase
import com.noteappexample.noteapp.room.Note
import com.noteappexample.noteapp.room.NoteDao
import com.noteappexample.noteapp.viewmodel.AddNoteScreenViewModel
import kotlinx.coroutines.async

class AddNoteScreen : Fragment() {

    private var _binding: FragmentAddNoteScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddNoteScreenViewModel

    private lateinit var navController: NavController

    private lateinit var noteDao: NoteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteScreenBinding.inflate(inflater, container, false)

        // Get the database instance
        // Use the noteDao to perform database operations
        // For example, you can call noteDao.getNotes() to get the notes
        val appDatabase = AppDatabase.getDatabase(requireContext())
        noteDao = appDatabase.noteDao()


        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity())[AddNoteScreenViewModel::class.java]

        // Initialize NavController
        navController = findNavController()

        binding.submitButton.setOnClickListener {

            lifecycleScope.async {
                noteDao.insertNote(
                    Note(
                        id = 0,
                        title = binding.outlinedTextField.editText?.text.toString(),
                        "",
                        "",
                        "",
                        false,
                        ""
                    )
                )

                navController.navigate(R.id.action_addNoteScreen_to_noteHomeScreen)

            }

        }

        return binding.root
    }


}