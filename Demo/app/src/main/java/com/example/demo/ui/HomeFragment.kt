package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demo.data.UserVM
import com.example.demo.databinding.FragmentHomeBinding
import com.example.demo.util.UserAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val nav by lazy { findNavController() }
    private val vm: UserVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        binding.btnRestore.setOnClickListener { vm.restore() }
        binding.btnDeleteAll.setOnClickListener { vm.deleteAll() }

        val adapter = UserAdapter { h, user ->
            h.binding.btnDelete.setOnClickListener { vm.delete(user.email) }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        vm.getUsersLD().observe(viewLifecycleOwner) {
            binding.txtCount.text = "${it.size} user(s)"
            adapter.submitList(it)
        }

        // -----------------------------------------------------------------------------------------
        return binding.root
    }

}








