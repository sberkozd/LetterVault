package com.sberkozd.lettervault.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.databinding.FragmentGridBinding
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addIcon.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
        }

        binding.gridIcon.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGridFragment())
        }
    }

}