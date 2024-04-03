package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.demo.databinding.FragmentPage1Binding
import com.example.demo.util.infoDialog

class Page1Fragment : Fragment() {

    private lateinit var binding: FragmentPage1Binding
    private val nav by lazy { findNavController() }
    private val auth: AuthVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPage1Binding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        binding.btnSecret.setOnClickListener { secret() }

        // TODO(9): Hide button if not login
        if (auth.getUser() == null) {
            binding.btnSecret.visibility =View.INVISIBLE
        }

        // -----------------------------------------------------------------------------------------
        return binding.root
    }

    private fun secret() {
        val user = auth.getUser()
        infoDialog("Hello, ${user?.name}.")
    }

}








