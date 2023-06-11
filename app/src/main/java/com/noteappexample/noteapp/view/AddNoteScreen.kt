package com.noteappexample.noteapp.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import java.util.*


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

        binding.switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Switch is checked (true)
                // Perform actions when the switch is turned ON
                binding.password.visibility = View.VISIBLE
            } else {
                // Switch is unchecked (false)
                // Perform actions when the switch is turned OFF
                binding.password.visibility = View.GONE
            }
        }


        binding.submitButton.setOnClickListener {

            lifecycleScope.async {
                noteDao.insertNote(
                    Note(
                        id = 0,
                        title = binding.outlinedTextField.editText?.text.toString(),
                        binding.content.editText?.text.toString(),
                        Calendar.getInstance().time.toString(),
                        "",
                        binding.switchMaterial.isChecked,
                        binding.password.editText?.text.toString(),
                        imageUrl = binding.imageAddScreenUrl.editText?.text.toString().trim()
                    )
                )

                navController.navigate(R.id.action_addNoteScreen_to_noteHomeScreen)

            }

        }

        binding.clickableImageUrl.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = binding.clickableImageUrl.text.toString()

            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)

            // Optional: Show a Toast message to indicate successful copy
            Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show()
        }

        binding.clickableImageUrl2.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = binding.clickableImageUrl2.text.toString()

            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)

            // Optional: Show a Toast message to indicate successful copy
            Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }





}