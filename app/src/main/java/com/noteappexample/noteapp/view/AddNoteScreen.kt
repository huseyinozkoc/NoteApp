package com.noteappexample.noteapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.noteappexample.noteapp.databinding.FragmentAddNoteScreenBinding
import com.noteappexample.noteapp.viewmodel.AddNoteScreenViewModel

class AddNoteScreen : Fragment() {

    private var _binding: FragmentAddNoteScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddNoteScreenViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteScreenBinding.inflate(inflater, container, false)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity())[AddNoteScreenViewModel::class.java]

        // Initialize NavController
        navController = findNavController()

        return binding.root
    }



}