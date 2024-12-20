package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.User
import com.example.demo.data.UserVM
import com.example.demo.databinding.FragmentRegisterBinding
import com.example.demo.util.cropToBlob
import com.example.demo.util.errorDialog

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val nav by lazy { findNavController() }
    private val vm: UserVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnRegister.setOnClickListener { register() }
        binding.btnBack.setOnClickListener { nav.navigateUp() }

        // -----------------------------------------------------------------------------------------
        return binding.root
    }

    private fun reset() {
        binding.edtEmail.text.clear()
        binding.edtPassword.text.clear()
        binding.edtName.text.clear()
        binding.imgPhoto.setImageDrawable(null)

        binding.edtEmail.requestFocus()
    }

    // Get-content launcher
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imgPhoto.setImageURI(it)
    }

    private fun select() {
        // Select file
        getContent.launch("image/*")
    }

    private fun register() {
        // Insert user
        val user = User(
            email    = binding.edtEmail.text.toString().trim(),
            password = binding.edtPassword.text.toString().trim(),
            name     = binding.edtName.text.toString().trim(),
            photo    = binding.imgPhoto.cropToBlob(300, 300)
        )

        val e = vm.validate(user)
        if (e != "") {
            errorDialog(e)
            return
        }

        vm.set(user)
        nav.navigateUp()
    }

}