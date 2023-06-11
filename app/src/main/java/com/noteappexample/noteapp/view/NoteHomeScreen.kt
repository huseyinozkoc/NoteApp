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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noteappexample.noteapp.R
import com.noteappexample.noteapp.databinding.FragmentNoteDetailScreenBinding
import com.noteappexample.noteapp.databinding.FragmentNoteHomeScreenBinding
import com.noteappexample.noteapp.repository.NoteRepository
import com.noteappexample.noteapp.room.AppDatabase
import com.noteappexample.noteapp.room.Note
import com.noteappexample.noteapp.room.NoteDao
import com.noteappexample.noteapp.view.adapters.NoteAdapter
import com.noteappexample.noteapp.viewmodel.NoteDetailScreenViewModel
import com.noteappexample.noteapp.viewmodel.NoteHomeScreenViewModel
import com.noteappexample.noteapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random

class NoteHomeScreen : Fragment() {

    private var _binding: FragmentNoteHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteHomeScreenViewModel
    private lateinit var factory: ViewModelFactory

    private lateinit var navController: NavController

    private lateinit var noteDao: NoteDao

    private lateinit var noteAdapter: NoteAdapter

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

            // Create a LinearLayoutManager to be used by the RecyclerView
            val layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerViewHomePage.layoutManager = layoutManager

            // Create an instance of the adapter
            noteAdapter = NoteAdapter(notes.toMutableList(),requireContext())
            // Set the adapter to the RecyclerView
            binding.recyclerViewHomePage.adapter = noteAdapter


        }

           // For Swipe Operations
           val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
               override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                   return false
               }

               override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                   val position = viewHolder.adapterPosition
                   val swipedItem = noteAdapter.getDataList()[position]

                   // Delete item from Room database using DAO
                   lifecycleScope.launch {
                       viewModel.deleteNote(swipedItem)
                   }

               }
           }

          // Attract Swipe Object to RecyclerView
           val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
           itemTouchHelper.attachToRecyclerView(binding.recyclerViewHomePage)




        binding.floatingButton.setOnClickListener {
            navController.navigate(R.id.action_noteHomeScreen_to_addNoteScreen)
        }


        binding.floatingButton2.setOnClickListener {

            lifecycleScope.async(Dispatchers.IO) {
                viewModel.insertNote(
                    Note(
                        id = 0,
                        title = "Hüsen",
                        "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.",
                         Calendar.getInstance().time.toString(),
                        "",
                        false,
                        ""
                    )
                )
            }
        }







        return binding.root
    }


}