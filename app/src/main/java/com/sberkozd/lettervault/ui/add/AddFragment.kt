package com.sberkozd.lettervault.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sberkozd.lettervault.R
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.databinding.FragmentAddBinding
import com.sberkozd.lettervault.ui.add.AddViewModel
import com.sberkozd.lettervault.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private val addViewModel: AddViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeIcon.setOnClickListener {
            findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
        }

        binding.confirmIcon.setOnClickListener {
            Toast.makeText(requireContext(),"Note Created!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
        }

        binding.timeIcon.setOnClickListener{
            // datepicker implement
        }

    }
}
